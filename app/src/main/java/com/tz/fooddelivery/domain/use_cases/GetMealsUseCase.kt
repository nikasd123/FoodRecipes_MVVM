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
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMealsUseCase @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val getTranslatedTextUseCase: GetTranslatedTextUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val translationDispatcher = Dispatchers.IO.limitedParallelism(5)
    private val translatedCache = ConcurrentHashMap<String, DishItem>()

    fun getDishes(): Flow<Result<List<DishItem>, NetworkError>> = channelFlow {
        when (val result = mealsRepository.getDishes()) {
            is Result.Success -> {
                try {
                    val processed = processDishes(result.data)
                    send(Result.Success(processed))
                } catch (e: Exception) {
                    send(Result.Error(mapTranslationError(e)))
                }
            }
            is Result.Error -> send(mapRepositoryError(result.error))
        }
        close()
    }.flowOn(translationDispatcher)

    fun getDishesByCategory(category: String): Flow<Result<List<DishItem>, NetworkError>> = channelFlow {
        when (val result = mealsRepository.getDishesByCategory(category)) {
            is Result.Success -> {
                try {
                    val processed = processDishes(result.data)
                    send(Result.Success(processed))
                } catch (e: Exception) {
                    send(Result.Error(mapTranslationError(e)))
                }
            }
            is Result.Error -> send(mapRepositoryError(result.error))
        }
        close()
    }.flowOn(translationDispatcher)

    private suspend fun processDishes(dishes: List<DishItem>): List<DishItem> = coroutineScope {
        dishes.map { dish ->
            translatedCache.getOrPut(dish.id) {
                try {
                    translateDish(dish)
                } catch (e: Exception) {
                    throw GetTranslatedTextUseCase.TranslationException(mapTranslationError(e))
                }
            }
        }
    }

    private suspend fun translateDish(dish: DishItem): DishItem = coroutineScope {
        val titleResult = getTranslatedTextUseCase(dish.title)
        val descResult = getTranslatedTextUseCase(dish.description)

        dish.copy(
            title = (titleResult as? Result.Success)?.data ?: dish.title,
            description = (descResult as? Result.Success)?.data ?: dish.description
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