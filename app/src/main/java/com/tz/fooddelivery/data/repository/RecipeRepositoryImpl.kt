package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.data.remote.dto.toDomain
import com.tz.fooddelivery.domain.models.MealRecipe
import com.tz.fooddelivery.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val mealsApi: MealsApi
): RecipeRepository {
    override suspend fun getMealRecipe(mealId: String): MealRecipe? = withContext(Dispatchers.IO) {
        try {
            val response = mealsApi.getMealRecipeById(mealId)
            response.mealsRecipe?.get(0)?.toDomain()
        } catch (e: Exception){
            throw e
        }
    }
}