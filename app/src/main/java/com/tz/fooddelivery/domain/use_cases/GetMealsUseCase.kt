package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.repository.MealsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMealsUseCase @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val getTranslatedTextUseCase: GetTranslatedTextUseCase
){
    suspend fun getDishes(): List<DishItem>? {
        val dishes = mealsRepository.getDishes()
        return dishes?.map { dishItem ->
            val translatedTitle = getTranslatedTextUseCase(dishItem.title)
            val translatedDescription = getTranslatedTextUseCase(dishItem.description)
            dishItem.copy(
                title = translatedTitle,
                description = translatedDescription
            )
        }
    }

    suspend fun getDishesByCategory(category: String): List<DishItem>? {
        val dishes = mealsRepository.getDishesByCategory(category)
        return dishes?.map { dishItem ->
            val translatedTitle = getTranslatedTextUseCase(dishItem.title)
            dishItem.copy(title = translatedTitle)
        }
    }
}