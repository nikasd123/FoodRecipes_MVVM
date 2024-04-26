package com.tz.fooddelivery.domain.repository

interface TranslationRepository {
    suspend fun getTranslatedText(textToTranslate: String): String
}