package com.tz.fooddelivery.domain.models

import com.tz.fooddelivery.data.local.entities.CategoryEntity

data class Category(
    val id: String,
    val category: String,
    var isActive: Boolean
)

internal fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        categoryId = id,
        categoryName = category
    )