package com.kaz4.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MealsResponseDto(
    @SerializedName("meals") val meals: List<MealItemDto>?
)