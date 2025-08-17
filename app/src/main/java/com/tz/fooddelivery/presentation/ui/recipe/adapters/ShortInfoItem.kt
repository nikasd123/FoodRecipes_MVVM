package com.tz.fooddelivery.presentation.ui.recipe.adapters

data class ShortInfoItem(
    val type: InfoType,
    val value: String
) {
    enum class InfoType {
        CATEGORY, TIME, AREA
    }
}