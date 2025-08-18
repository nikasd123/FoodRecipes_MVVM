package com.tz.fooddelivery.domain.models

import com.tz.fooddelivery.data.local.entities.CategoryEntity

data class Category(
    val id: String = "1",
    val category: String = "beef",
    val ruCategory: String = "",
    var isActive: Boolean = true
)

val DefaultCategory = Category()

internal fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        categoryId = id,
        categoryName = category,
        originalName = ruCategory
    )