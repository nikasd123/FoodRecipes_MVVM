package com.kaz4.domain.models

data class MealRecipe(
    val idMeal: String,
    val mealTitle: String,
    val mealCategory: String,
    val area: String,
    val mealRecipe: String,
    val mealImage: String,
    val strYoutube: String,
    val ingredients: String
)
