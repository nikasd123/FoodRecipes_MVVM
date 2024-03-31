package com.tz.fooddelivery.data.dto

import com.google.gson.annotations.SerializedName
import com.tz.fooddelivery.domain.models.CategoriesResponse

data class CategoriesResponseDto(
    @SerializedName("categories") val categories: List<CategoryDto>?
)

internal fun CategoriesResponseDto.toDomain(): CategoriesResponse =
    CategoriesResponse(
        categories = categories?.map { it.toDomain() }
    )