package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.cache.TranslationCacheImpl
import com.tz.fooddelivery.data.remote.api.TranslationApi
import com.tz.fooddelivery.domain.cache.TranslationCache
import com.tz.fooddelivery.domain.repository.TranslationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationRepositoryImpl @Inject constructor(
    private val translationApi: TranslationApi,
    private val cache: TranslationCache = TranslationCacheImpl()
) : TranslationRepository {

    override suspend fun getRussianText(textToTranslate: String): String =
        translateText(
            text = textToTranslate,
            sourceLanguage = "en",
            targetLanguage = "ru"
        )

    override suspend fun getEnglishText(textToTranslate: String): String =
        translateText(
            text = textToTranslate,
            sourceLanguage = "ru",
            targetLanguage = "en"
        )

    private suspend fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): String = withContext(Dispatchers.IO) {
        try {
            cache.get(text, sourceLanguage, targetLanguage)?.let {
                return@withContext it
            }

            val response = translationApi.translateText(
                sourceLanguage = sourceLanguage,
                destinationLanguage = targetLanguage,
                text = text
            )

            val translatedText = response.translatedText ?: text
            cache.put(text, translatedText, sourceLanguage, targetLanguage)

            translatedText
        } catch (e: Exception) {
            cache.get(text, sourceLanguage, targetLanguage) ?: text
        }
    }
}