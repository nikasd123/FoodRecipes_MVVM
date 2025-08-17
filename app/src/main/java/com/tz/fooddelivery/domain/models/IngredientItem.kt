package com.tz.fooddelivery.domain.models

data class IngredientItem(
    val translatedName: String,
    val originalName: String,
    val translatedMeasure: String,
    val originalMeasure: String,
    val imageUrl: String
)