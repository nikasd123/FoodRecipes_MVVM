package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.repository.TranslationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTranslatedTextUseCase @Inject constructor(
    private val translationRepository: TranslationRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val translationDispatcher = Dispatchers.IO.limitedParallelism(5)

    suspend operator fun invoke(textToTranslate: String): String = withContext(translationDispatcher) {
        try {
            translationRepository.getRussianText(textToTranslate)
        } catch (e: Exception) {
            textToTranslate // Fallback to original text
        }
    }

    suspend fun getEnglishText(textToTranslate: String): String = withContext(translationDispatcher) {
        try {
            translationRepository.getEnglishText(textToTranslate)
        } catch (e: Exception) {
            textToTranslate
        }
    }

    fun translateFlow(texts: List<String>): Flow<Pair<String, String>> = flow {
        coroutineScope {
            texts.map { text ->
                async(translationDispatcher) {
                    text to invoke(text)
                }
            }.forEach { deferred ->
                emit(deferred.await())
            }
        }
    }
}
