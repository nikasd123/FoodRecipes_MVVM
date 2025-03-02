package com.tz.fooddelivery.domain.models

import com.tz.fooddelivery.data.local.entities.CategoryEntity

data class Category(
    val id: String,
    val category: String,
    val originalName: String = "",
    var isActive: Boolean = false,
    val isLoading: Boolean = false
)

internal fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        categoryId = id,
        categoryName = category,
        originalName = originalName
    )