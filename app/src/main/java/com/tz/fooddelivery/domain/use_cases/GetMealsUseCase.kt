package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMealsUseCase @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val getTranslatedTextUseCase: GetTranslatedTextUseCase
){
    private val translationDispatcher = Dispatchers.IO.limitedParallelism(5)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getDishes(): Flow<DishItem> = flow {
        val dishes = mealsRepository.getDishes() ?: emptyList()
        emitAll(dishes.asFlow())
    }.flatMapMerge(translationDispatcher) { dish ->
        flow { emit(translateDish(dish)) }
    }

    private suspend fun translateDish(dish: DishItem): DishItem = coroutineScope {
        val title = withContext(translationDispatcher) {
            getTranslatedTextUseCase(dish.title)
        }
        val description = withContext(translationDispatcher) {
            getTranslatedTextUseCase(dish.description)
        }
        dish.copy(title = title, description = description)
    }

    private val translatedCache = mutableMapOf<String, DishItem>()

    fun getDishesByCategory(category: String): Flow<DishItem> = flow {
        val dishes = mealsRepository.getDishesByCategory(category) ?: emptyList()
        dishes.forEach { dish ->
            translatedCache[dish.id]?.let {
                emit(it)
            } ?: run {
                val translated = translateDish(dish)
                translatedCache[dish.id] = translated
                emit(translated)
            }
        }
    }

//    private suspend fun translateDish(dish: DishItem): DishItem = coroutineScope {
//        val titleDeferred = async { getTranslatedTextUseCase(dish.title) }
//        val descDeferred = async { getTranslatedTextUseCase(dish.description) }
//
//        dish.copy(
//            title = titleDeferred.await(),
//            description = descDeferred.await()
//        )
//    }

    private suspend fun convertRussianToEnglishText(text: String): String =
        getTranslatedTextUseCase.getEnglishText(text)

}