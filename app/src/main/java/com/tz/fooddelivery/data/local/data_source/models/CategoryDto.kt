package com.tz.fooddelivery.data.local.data_source.models

import com.google.gson.annotations.SerializedName
import com.tz.fooddelivery.domain.models.Category

data class CategoryDto(
    @SerializedName("idCategory") val idCategory: String,
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("ruCategory") val ruCategory: String
) {
    fun toDomain() = Category(id = idCategory, category = strCategory, ruCategory = ruCategory)
}