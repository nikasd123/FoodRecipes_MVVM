package com.tz.fooddelivery.domain.repository

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.MealRecipe
import kotlinx.coroutines.flow.Flow

interface MealRecipeRepository {
    suspend fun getDishById(id: String): Flow<Result<MealRecipe, DataError>>
}