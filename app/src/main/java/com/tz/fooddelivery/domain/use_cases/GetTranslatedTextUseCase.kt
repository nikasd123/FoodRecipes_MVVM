package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.common.TranslationError
import com.tz.fooddelivery.domain.repository.TranslationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTranslatedTextUseCase @Inject constructor(
    private val translationRepository: TranslationRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val translationDispatcher = Dispatchers.IO.limitedParallelism(5)

    suspend operator fun invoke(text: String): Result<String, TranslationError> =
        withContext(translationDispatcher) {
            try {
                Result.Success(translationRepository.getRussianText(text))
            } catch (e: Exception) {
                Result.Error(mapError(e))
            }
        }

    fun translateBatch(texts: List<String>): Flow<Result<String, TranslationError>> = flow {
        coroutineScope {
            texts.map { text ->
                async {
                    invoke(text)
                }
            }.forEach { deferred ->
                emit(deferred.await())
            }
        }
    }

    private fun mapError(e: Exception): TranslationError =
        when (e) {
            is IOException -> TranslationError.NETWORK_ERROR
            else -> TranslationError.UNKNOWN_ERROR
        }

    internal class TranslationException(val error: NetworkError) : Exception()
}
