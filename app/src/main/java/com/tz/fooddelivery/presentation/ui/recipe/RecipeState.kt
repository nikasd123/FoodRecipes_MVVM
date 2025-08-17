package com.tz.fooddelivery.presentation.ui.recipe

import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.models.MealRecipe

sealed interface RecipeState {
    data object Loading: RecipeState
    data class Success(val recipes: List<MealRecipe>) : RecipeState
    data class Error(val error: NetworkError, val message: String, val id: String) : RecipeState
}