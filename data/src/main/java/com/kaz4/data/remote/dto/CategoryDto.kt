package com.kaz4.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.kaz4.domain.models.Category

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