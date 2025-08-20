package com.tz.fooddelivery.domain.models

import com.tz.fooddelivery.data.local.entities.CategoryEntity

data class Category(
    val id: String,
    val category: String,
    val ruCategory: String,
    var isActive: Boolean = false
)

val DefaultCategory = Category(
    id = "1",
    category = "Beef",
    ruCategory = "Говядина"
)

internal fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        categoryId = id,
        categoryName = category,
        originalName = ruCategory
    )