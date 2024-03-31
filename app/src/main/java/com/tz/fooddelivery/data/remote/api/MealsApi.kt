package com.tz.fooddelivery.data.remote.api

import com.tz.fooddelivery.data.remote.dto.CategoriesResponseDto
import com.tz.fooddelivery.data.remote.dto.MealsResponseDto
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