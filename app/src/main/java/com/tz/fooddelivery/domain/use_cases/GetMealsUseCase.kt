package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMealsUseCase @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val getTranslatedTextUseCase: GetTranslatedTextUseCase
){
    private val translationDispatcher = Dispatchers.IO.limitedParallelism(5)

    fun getDishes(): Flow<DishItem> = flow {
        val dishes = mealsRepository.getDishes() ?: emptyList()

        coroutineScope {
            dishes.map { dish ->
                async(translationDispatcher) {
                    translateDish(dish)
                }
            }.forEach { deferred ->
                emit(deferred.await())
            }
        }
    }

    fun getDishesByCategory(category: String): Flow<DishItem>? = flow{
        val dishes = mealsRepository.getDishesByCategory(convertRussianToEnglishText(category)) ?: emptyList()

        coroutineScope {
            dishes.map { dish ->
                async(translationDispatcher) {
                    translateDish(dish)
                }
            }.forEach { deferred ->
                emit(deferred.await())
            }
        }
    }

    private suspend fun translateDish(dish: DishItem): DishItem = coroutineScope {
        val titleDeferred = async { getTranslatedTextUseCase(dish.title) }
        val descDeferred = async { getTranslatedTextUseCase(dish.description) }

        dish.copy(
            title = titleDeferred.await(),
            description = descDeferred.await()
        )
    }

    private suspend fun convertRussianToEnglishText(text: String): String =
        getTranslatedTextUseCase.getEnglishText(text)

}