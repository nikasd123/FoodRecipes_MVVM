package com.tz.fooddelivery.domain.models

import com.tz.fooddelivery.data.local.entities.MealEntity

data class MealItem(
    val id: String,
    val image: String,
    val title: String,
    val category: String,
    val description: String
)

internal fun MealItem.toEntity(): MealEntity =
    MealEntity(
        dishId = id,
        dishImage = image,
        dishTitle = title,
        dishCategory = category,
        description = description
    )