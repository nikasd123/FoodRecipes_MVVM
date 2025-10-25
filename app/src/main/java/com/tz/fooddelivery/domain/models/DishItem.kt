package com.tz.fooddelivery.domain.models

import com.tz.fooddelivery.data.local.entities.DishEntity

data class DishItem(
    val id: String,
    val image: String,
    val title: String,
    val category: String,
    val description: String,
    val isFavorite: Boolean = false
)

internal val EmptyDishItem = DishItem("0", "", "", "", "")

internal fun DishItem.toEntity(): DishEntity =
    DishEntity(
        dishId = id,
        dishImage = image,
        dishTitle = title,
        dishCategory = category,
        description = description,
        isFavorite = isFavorite
    )