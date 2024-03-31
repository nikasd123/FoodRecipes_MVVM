package com.tz.fooddelivery.domain.models

data class DishItem(
    val id: String,
    val image: String,
    val title: String,
    val category: String,
    val description: String
)