package com.tz.fooddelivery.data.local.data_source.models

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories") val categories: List<CategoryDto>
)