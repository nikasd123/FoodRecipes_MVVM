package com.tz.fooddelivery.data.api

import com.tz.fooddelivery.data.dto.CategoriesResponse
import com.tz.fooddelivery.data.dto.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {
    @GET("search.php?s")
    suspend fun getMeals(): MealsResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse
}