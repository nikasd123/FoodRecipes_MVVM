package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.repository.TranslationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTranslatedTextUseCase @Inject constructor(
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
