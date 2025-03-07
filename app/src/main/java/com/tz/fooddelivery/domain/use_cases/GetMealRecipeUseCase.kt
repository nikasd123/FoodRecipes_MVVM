package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.common.mappers.mapTranslationError
import com.tz.fooddelivery.domain.models.MealRecipe
import com.tz.fooddelivery.domain.repository.MealRecipeRepository
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMealRecipeUseCase @Inject constructor(
    private val mealRecipeRepository: MealRecipeRepository,
    private val translateTextUseCase: GetTranslatedTextUseCase
) {
    private val translatedCache = ConcurrentHashMap<String, MealRecipe>()

    suspend fun getMealRecipe(id: String): Result<List<MealRecipe>, NetworkError> =
        when (val result = mealRecipeRepository.getMealRecipe(id)){
            is Result.Success -> {
                try {
                    Result.Success(processMealRecipe(result.data))
                } catch (e: Exception){
                    Result.Error(mapTranslationError(e))
                }
            }
            is Result.Error -> mapRepositoryError(result.error)
        }

    private suspend fun processMealRecipe(recipes: List<MealRecipe>): List<MealRecipe> = coroutineScope {
        recipes.map { recipe ->
            translatedCache.getOrPut(recipe.idMeal) {
                translateRecipe(recipe)
            }
        }
    }

    private suspend fun translateRecipe(recipe: MealRecipe): MealRecipe {
        val recipeResult = translateTextUseCase(recipe.mealRecipe)
        val ingredientsResult = translateTextUseCase(recipe.ingredients)
        return recipe.copy(
            mealRecipe = (recipeResult as? Result.Success)?.data ?: recipe.mealRecipe,
            ingredients = (ingredientsResult as? Result.Success)?.data ?: recipe.ingredients
        )
    }

    private fun mapRepositoryError(error: DataError): Result.Error<Nothing, NetworkError> =
        Result.Error(
            when (error) {
                is DataError.Network -> when (error) {
                    DataError.Network.NO_INTERNET -> NetworkError.NETWORK_ERROR
                    DataError.Network.DATA_NOT_FOUND -> NetworkError.DATA_NOT_FOUND
                    else -> NetworkError.UNKNOWN_ERROR
                }
                is DataError.Local -> NetworkError.UNKNOWN_ERROR
            }
        )
}