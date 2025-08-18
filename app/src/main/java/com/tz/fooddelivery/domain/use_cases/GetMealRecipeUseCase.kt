package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.MealRecipe
import com.tz.fooddelivery.domain.repository.MealRecipeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMealRecipeUseCase @Inject constructor(
    private val mealRecipeRepository: MealRecipeRepository
) {
    suspend fun getMealRecipe(id: String): Result<MealRecipe, NetworkError> {
        return try {
            when (val result = mealRecipeRepository.getDishById(id)) {
                is Result.Success -> Result.Success(result.data)
                is Result.Error -> Result.Error(mapRepositoryError(result.error))
            }
        } catch (e: Exception) {
            Result.Error(NetworkError.UNKNOWN_ERROR)
        }
    }

    private fun mapRepositoryError(error: DataError): NetworkError {
        return when (error) {
            DataError.Local.DATABASE_ERROR -> NetworkError.DATA_NOT_FOUND
            else -> NetworkError.UNKNOWN_ERROR
        }
    }
}