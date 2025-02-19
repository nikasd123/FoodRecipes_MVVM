package com.tz.fooddelivery.data.cache

import com.tz.fooddelivery.domain.cache.TranslationCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationCacheImpl @Inject constructor() : TranslationCache {
    private companion object {
        const val MAX_SIZE = 100
        const val TTL = 24 * 60 * 60 * 1000L
    }

    private val cache = object : LinkedHashMap<CacheKey, CacheValue>(MAX_SIZE, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<CacheKey, CacheValue>?): Boolean {
            return size > MAX_SIZE
        }
    }

    override fun get(
        originalText: String,
        sourceLang: String,
        targetLang: String
    ): String? {
        val key = CacheKey(originalText, sourceLang, targetLang)
        return synchronized(cache) {
            cache[key]?.takeIf { System.currentTimeMillis() - it.timestamp < TTL }?.value
        }
    }

    override fun put(
        originalText: String,
        translatedText: String,
        sourceLang: String,
        targetLang: String
    ) {
        val key = CacheKey(originalText, sourceLang, targetLang)
        synchronized(cache) {
            cache[key] = CacheValue(translatedText, System.currentTimeMillis())
        }
    }

    private data class CacheKey(
        val text: String,
        val sourceLang: String,
        val targetLang: String
    )

    private data class CacheValue(
        val value: String,
        val timestamp: Long
    )
}