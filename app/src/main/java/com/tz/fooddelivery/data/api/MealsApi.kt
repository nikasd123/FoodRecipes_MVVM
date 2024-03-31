package com.tz.fooddelivery.data.api

import com.tz.fooddelivery.data.dto.MealsResponse
import retrofit2.http.GET

interface MealsApi {
    @GET("search.php?s")
    suspend fun getMeals(): MealsResponse
}