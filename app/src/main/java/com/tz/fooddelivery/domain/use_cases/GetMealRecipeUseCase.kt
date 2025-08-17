package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.common.TranslationError
import com.tz.fooddelivery.domain.common.mappers.mapTranslationError
import com.tz.fooddelivery.domain.models.IngredientItem
import com.tz.fooddelivery.domain.models.MealRecipe
import com.tz.fooddelivery.domain.repository.MealRecipeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

    private suspend fun translateRecipe(recipe: MealRecipe): MealRecipe = coroutineScope {
        val areaDeferred = async { translateTextUseCase(recipe.area) }
        val categoryDeferred = async { translateTextUseCase(recipe.mealCategory) }
        val recipeDeferred = async { translateTextUseCase(recipe.mealRecipe) }

        val originalIngredients: List<IngredientItem> = recipe.ingredients
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { name ->
                IngredientItem(
                    translatedName = name,
                    originalName = name,
                    imageUrl = buildImageUrl(name)
                )
            }

        val ingredientsDeferred = originalIngredients.map { ingredient ->
            async {
                val translationResult = translateTextUseCase(ingredient.originalName)
                ingredient.copy(
                    translatedName = when (translationResult) {
                        is Result.Success -> translationResult.data
                        is Result.Error -> ingredient.originalName
                    }
                )
            }
        }

        val areaResult = areaDeferred.await()
        val categoryResult = categoryDeferred.await()
        val recipeResult = recipeDeferred.await()
        val ingredientItems = ingredientsDeferred.awaitAll()

        val translatedIngredientsString = ingredientItems.joinToString(", ") { it.translatedName }

        recipe.copy(
            mealRecipe = getTranslatedText(recipeResult) ?: recipe.mealRecipe,
            ingredients = translatedIngredientsString,
            ingredientsList = ingredientItems,
            mealCategory = getTranslatedText(categoryResult) ?: recipe.mealCategory,
            area = getTranslatedText(areaResult) ?: recipe.area
        )
    }

    private fun getTranslatedText(result: Result<String, TranslationError>): String? {
        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> null
        }
    }

    private fun buildImageUrl(ingredientName: String): String {
        val formattedName = ingredientName
            .trim()
            .replace(" ", "_")
            .lowercase()
        return "https://www.themealdb.com/images/ingredients/$formattedName-medium.png"
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