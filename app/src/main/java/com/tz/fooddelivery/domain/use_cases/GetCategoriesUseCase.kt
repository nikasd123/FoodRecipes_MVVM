package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.common.mappers.mapTranslationError
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val translateUseCase: GetTranslatedTextUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val translationDispatcher = Dispatchers.IO.limitedParallelism(5)
    private val translatedCache = ConcurrentHashMap<String, Category>()

    suspend fun getCategories(): Flow<Result<List<Category>, NetworkError>> =
        mealsRepository.getCategories().map { result ->
            when (result) {
                is Result.Success -> {
                    try {
                        Result.Success(processCategories(result.data))
                    } catch (e: Exception) {
                        Result.Error(mapTranslationError(e))
                    }
                }
                is Result.Error -> mapRepositoryError(result.error)
            }
        }.flowOn(translationDispatcher)

    private suspend fun processCategories(categories: List<Category>): List<Category> = coroutineScope {
        categories.map { category ->
            translatedCache.getOrPut(category.id) {
                translateCategory(category)
            }
        }
    }

    private suspend fun translateCategory(category: Category): Category {
        val titleResult = translateUseCase(category.category)
        return category.copy(
            category = (titleResult as? Result.Success)?.data ?: category.category
        )
    }

    private fun mapRepositoryError(error: DataError): Result.Error<Nothing, NetworkError> =
        Result.Error(
            when (error) {
                is DataError.Network -> when (error) {
                    DataError.Network.NO_INTERNET -> NetworkError.NETWORK_ERROR
                    DataError.Network.DATA_NOT_FOUND -> NetworkError.DATA_NOT_FOUND
                    else -> NetworkError.UNKNOWN_ERROR
                }
                is DataError.Local -> NetworkError.UNKNOWN_ERROR
            }
        )
}