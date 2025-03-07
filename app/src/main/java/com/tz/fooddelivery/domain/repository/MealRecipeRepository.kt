package com.tz.fooddelivery.domain.repository

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.MealRecipe

interface MealRecipeRepository {
    suspend fun getMealRecipe(id: String): Result<List<MealRecipe>, DataError>
}