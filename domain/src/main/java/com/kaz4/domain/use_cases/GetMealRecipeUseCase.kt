package com.kaz4.domain.use_cases

import com.kaz4.domain.models.MealRecipe
import com.kaz4.domain.repository.RecipeRepository

class GetMealRecipeUseCase(
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