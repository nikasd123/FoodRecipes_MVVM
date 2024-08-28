package com.kaz4.data.repository

import com.kaz4.data.remote.api.MealsApi
import com.kaz4.data.remote.dto.toDomain
import com.kaz4.domain.models.MealRecipe
import com.kaz4.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
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