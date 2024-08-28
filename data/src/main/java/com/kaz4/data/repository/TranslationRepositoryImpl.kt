package com.kaz4.data.repository

import com.kaz4.data.remote.api.TranslationApi
import com.kaz4.domain.repository.TranslationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TranslationRepositoryImpl(
    private val translationApi: TranslationApi
): TranslationRepository {
    override suspend fun getRussianText(textToTranslate: String): String = withContext(Dispatchers.IO) {
        return@withContext try {
            translationApi.translateText(
                sourceLanguage = "en",
                destinationLanguage = "ru",
                text = textToTranslate
            ).translatedText.toString()
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun getEnglishText(textToTranslate: String): String = withContext(Dispatchers.IO) {
        return@withContext try {
            translationApi.translateText(
                sourceLanguage = "ru",
                destinationLanguage = "en",
                text = textToTranslate
            ).translatedText.toString()
        } catch (e: Exception){
            throw e
        }
    }
}