package com.kaz4.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesResponseDto(
    @SerializedName("categories") val categories: List<CategoryDto>?
)