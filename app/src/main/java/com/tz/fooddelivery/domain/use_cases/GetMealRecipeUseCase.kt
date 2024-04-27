package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.models.MealRecipe
import com.tz.fooddelivery.domain.repository.RecipeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMealRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val getTranslatedTextUseCase: GetTranslatedTextUseCase
){
    suspend fun getMealRecipe(mealId: String): MealRecipe? =
        translateMealRecipe(recipeRepository.getMealRecipe(mealId))

    private suspend fun translateMealRecipe(mealRecipe: MealRecipe?): MealRecipe?{
        val translatedTitle = getTranslatedTextUseCase(mealRecipe?.mealTitle ?: "")
        val translatedRecipe = getTranslatedTextUseCase(mealRecipe?.mealRecipe ?: "")
        val translatedIngredients = getTranslatedTextUseCase(mealRecipe?.ingredients ?: "")

        return mealRecipe?.copy(
            mealTitle = translatedTitle,
            mealRecipe = translatedRecipe,
            ingredients = translatedIngredients
        )
    }
}