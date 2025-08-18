package com.tz.fooddelivery.data.local.data_source.models

import com.google.gson.annotations.SerializedName
import com.tz.fooddelivery.domain.models.DishItem

data class DishItemDto(
    @SerializedName("idMeal") val id: String?,
    @SerializedName("strMeal") val title: String?,
    @SerializedName("strMealThumb") val image: String?
) {
    fun toDomain(category: String) = DishItem(
        id = id ?: "",
        title = title ?: "",
        image = image ?: "",
        category = category,
        description = ""
    )
}