package com.kaz4.domain.repository

interface TranslationRepository {
    suspend fun getRussianText(textToTranslate: String): String
    suspend fun getEnglishText(textToTranslate: String): String
}