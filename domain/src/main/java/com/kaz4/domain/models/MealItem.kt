package com.kaz4.domain.models

data class MealItem(
    val id: String,
    val image: String,
    val title: String,
    val testImage: Int = 0,
    val category: String,
    val description: String
)