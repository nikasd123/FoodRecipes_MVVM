package com.tz.fooddelivery.data.dto

import com.google.gson.annotations.SerializedName
import com.tz.fooddelivery.domain.models.Category

data class CategoryDto (
    @SerializedName("strCategory") val strCategory: String?
)

internal fun CategoryDto.toDomain(): Category =
    Category(
        category = strCategory ?: "",
        isActive = false
    )