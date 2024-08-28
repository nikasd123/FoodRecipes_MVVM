package com.kaz4.domain.use_cases

import com.kaz4.domain.models.MealItem
import com.kaz4.domain.repository.MealsRepository

class GetMealsUseCase(
    private val mealsRepository: MealsRepository,
    private val getTranslatedTextUseCase: GetTranslatedTextUseCase
){
    suspend fun getMeals(): List<MealItem>? {
        val dishes = mealsRepository.getMeals()
        return dishes?.map { dishItem ->
            val translatedTitle = getTranslatedTextUseCase(dishItem.title)
            val translatedDescription = getTranslatedTextUseCase(dishItem.description)
            dishItem.copy(
                title = translatedTitle,
                description = translatedDescription
            )
        }
    }

    suspend fun getMealsByCategory(category: String): List<MealItem>? {
        val dishes = mealsRepository.getMealsByCategory(convertRussianToEnglishText(category))

        return dishes?.map { dishItem ->
            val translatedTitle = getTranslatedTextUseCase(dishItem.title)
            dishItem.copy(title = translatedTitle)
        }
    }

    private suspend fun convertRussianToEnglishText(text: String): String =
        getTranslatedTextUseCase.getEnglishText(text)

}