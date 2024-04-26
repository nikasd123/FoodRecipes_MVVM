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
            val translatedText = translationRepository.getTranslatedText(textToTranslate)
            translatedText
        } catch (e: Exception) {
            textToTranslate
        }
}
