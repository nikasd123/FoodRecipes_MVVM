package com.kaz4.domain.models

data class MealItem(
    val id: String,
    val image: String = "",
    val testImage: Int = 0,
    val title: String,
    val category: String,
    val description: String
)
