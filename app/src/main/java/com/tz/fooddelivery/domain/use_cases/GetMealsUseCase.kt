package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.common.mappers.mapTranslationError
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalCoroutinesApi::class)
class GetMealsUseCase @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val getTranslatedTextUseCase: GetTranslatedTextUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val translationDispatcher = Dispatchers.IO.limitedParallelism(5)
    private val translatedCache = ConcurrentHashMap<String, DishItem>()

    suspend fun getDishes(): Flow<Result<DishItem, NetworkError>> =
        mealsRepository.getDishes()
            .flatMapConcat { result -> processRepositoryResult(result) }
            .flowOn(translationDispatcher)

    suspend fun getDishesByCategory(category: String): Flow<Result<DishItem, NetworkError>> =
        mealsRepository.getDishesByCategory(category)
            .flatMapConcat { result -> processRepositoryResult(result) }
            .flowOn(translationDispatcher)

    private fun processRepositoryResult(result: Result<List<DishItem>, DataError>): Flow<Result<DishItem, NetworkError>> =
        flow {
            when (result) {
                is Result.Success -> {
                    result.data.forEach { dish ->
                        emit(processDishResult(dish))
                    }
                }
                is Result.Error -> {
                    emit(Result.Error(mapRepositoryError(result.error)))
                }
            }
        }

    private suspend fun processDishResult(dish: DishItem): Result<DishItem, NetworkError> =
        try {
            Result.Success(processDish(dish))
        } catch (e: Exception) {
            Result.Error(mapTranslationError(e))
        }

    private suspend fun processDish(dish: DishItem): DishItem = coroutineScope {
        translatedCache.getOrPut(dish.id) {
            translateDish(dish)
        }
    }

    private suspend fun translateDish(dish: DishItem): DishItem {
        val translatedTitle = translateText(dish.title)
        val translatedDesc = translateText(dish.description)

        return dish.copy(
            title = translatedTitle,
            description = translatedDesc
        )
    }

    private suspend fun translateText(text: String): String =
        when (val result = getTranslatedTextUseCase(text)) {
            is Result.Success -> result.data
            else -> text
        }

    private fun mapRepositoryError(error: DataError): NetworkError =
        when (error) {
            is DataError.Network -> when (error) {
                DataError.Network.NO_INTERNET -> NetworkError.NETWORK_ERROR
                DataError.Network.DATA_NOT_FOUND -> NetworkError.DATA_NOT_FOUND
                else -> NetworkError.UNKNOWN_ERROR
            }
            is DataError.Local -> NetworkError.UNKNOWN_ERROR
        }
}

