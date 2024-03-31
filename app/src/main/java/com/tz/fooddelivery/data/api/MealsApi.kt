package com.tz.fooddelivery.data.api

import com.tz.fooddelivery.data.dto.CategoriesResponseDto
import com.tz.fooddelivery.data.dto.MealsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {
    @GET("search.php?s")
    suspend fun getMeals(): MealsResponseDto

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponseDto

    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponseDto
}