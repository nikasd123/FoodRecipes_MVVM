package com.tz.fooddelivery.domain.cache

interface TranslationCache {
    fun get(
        originalText: String,
        sourceLang: String,
        targetLang: String
    ): String?

    fun put(
        originalText: String,
        translatedText: String,
        sourceLang: String,
        targetLang: String
    )
}