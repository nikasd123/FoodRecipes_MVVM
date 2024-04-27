package com.tz.fooddelivery.domain.repository

import com.tz.fooddelivery.domain.models.MealRecipe

interface RecipeRepository {
    suspend fun getMealRecipe(mealId: String): MealRecipe?
}