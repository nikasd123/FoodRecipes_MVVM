package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.remote.api.TranslationApi
import com.tz.fooddelivery.domain.repository.TranslationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TranslationRepositoryImpl @Inject constructor(
    private val translationApi: TranslationApi
): TranslationRepository {
    override suspend fun getTranslatedText(textToTranslate: String): String = withContext(Dispatchers.IO) {
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
}