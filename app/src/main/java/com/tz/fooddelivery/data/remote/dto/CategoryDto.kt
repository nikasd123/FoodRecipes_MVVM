package com.tz.fooddelivery.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.tz.fooddelivery.domain.models.Category

data class CategoryDto (
    @SerializedName("idCategory") val id: String?,
    @SerializedName("strCategory") val strCategory: String?
)

internal fun CategoryDto.toDomain(): Category =
    Category(
        id = id ?: "",
        category = strCategory ?: "",
        isActive = false
    )