package com.tz.fooddelivery.domain.repository

interface TranslationRepository {
    suspend fun getRussianText(textToTranslate: String): String
    suspend fun getEnglishText(textToTranslate: String): String
}