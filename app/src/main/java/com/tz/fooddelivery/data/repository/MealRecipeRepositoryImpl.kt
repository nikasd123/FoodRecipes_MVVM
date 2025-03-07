package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.data.remote.dto.toDomain
import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.common.mappers.mapError
import com.tz.fooddelivery.domain.models.MealRecipe
import com.tz.fooddelivery.domain.repository.MealRecipeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealRecipeRepositoryImpl @Inject constructor(
    private val api: MealsApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): MealRecipeRepository {
    override suspend fun getMealRecipe(id: String): Result<List<MealRecipe>, DataError> = withContext(ioDispatcher){
        try {
            val response = api.getMealRecipeById(id = id).mealsRecipe?.map {
                it.toDomain()
            } ?: emptyList()
            Result.Success(response)
        } catch (e: Exception){
            Result.Error(mapError(e))
        }
    }
}