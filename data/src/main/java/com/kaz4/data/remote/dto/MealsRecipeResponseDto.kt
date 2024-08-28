package com.kaz4.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MealsRecipeResponseDto(
    @SerializedName("meals") val mealsRecipe: List<MealRecipeDto>?
)
