package com.kaz4.domain.repository

import com.kaz4.domain.models.MealRecipe

interface RecipeRepository {
    suspend fun getMealRecipe(mealId: String): MealRecipe?
}