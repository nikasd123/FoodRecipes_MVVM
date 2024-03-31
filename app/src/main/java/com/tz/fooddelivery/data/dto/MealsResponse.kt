package com.tz.fooddelivery.data.dto

import com.google.gson.annotations.SerializedName
import com.tz.fooddelivery.domain.models.DishesResponse

data class MealsResponseDto(
    @SerializedName("meals") val meals: List<DishItemDto>?
)

internal fun MealsResponseDto.toDomain(): DishesResponse =
    DishesResponse(
        meals = meals?.map { it.toDomain() }
    )