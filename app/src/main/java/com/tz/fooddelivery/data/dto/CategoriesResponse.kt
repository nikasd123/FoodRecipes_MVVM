package com.tz.fooddelivery.data.dto

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories") val categories: List<CategoryDto>?
)
