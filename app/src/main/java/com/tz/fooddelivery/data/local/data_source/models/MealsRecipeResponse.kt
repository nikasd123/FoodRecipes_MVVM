package com.tz.fooddelivery.data.local.data_source.models

import com.google.gson.annotations.SerializedName

data class MealsRecipeResponse(
    @SerializedName("meals") val meals: List<DishRecipeDto>
)