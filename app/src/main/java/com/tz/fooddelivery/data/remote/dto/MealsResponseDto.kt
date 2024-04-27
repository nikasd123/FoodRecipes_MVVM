package com.tz.fooddelivery.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MealsResponseDto(
    @SerializedName("meals") val meals: List<MealItemDto>?
)