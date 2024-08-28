package com.kaz4.domain.use_cases

import com.kaz4.domain.repository.TranslationRepository

class GetTranslatedTextUseCase(
    private val translationRepository: TranslationRepository
) {
    suspend operator fun invoke(textToTranslate: String): String =
        try {
            val translatedText = translationRepository.getRussianText(textToTranslate)
            translatedText
        } catch (e: Exception) {
            textToTranslate
        }

    suspend fun getEnglishText(textToTranslate: String): String =
        try {
            val translatedText = translationRepository.getEnglishText(textToTranslate)
            translatedText
        } catch (e: Exception) {
            textToTranslate
        }
}
