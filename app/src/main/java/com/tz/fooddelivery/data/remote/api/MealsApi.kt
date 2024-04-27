package com.tz.fooddelivery.data.remote.api

import com.tz.fooddelivery.data.remote.dto.CategoriesResponseDto
import com.tz.fooddelivery.data.remote.dto.MealsRecipeResponseDto
import com.tz.fooddelivery.data.remote.dto.MealsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {
    @GET(GET_MEALS)
    suspend fun getMeals(): MealsResponseDto

    @GET(GET_MEALS_BY_CATEGORY)
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponseDto

    @GET(GET_CATEGORIES)
    suspend fun getCategories(): CategoriesResponseDto

    @GET(GET_MEAL_RECIPE)
    suspend fun getMealRecipeById(@Query("i") id: String): MealsRecipeResponseDto

    companion object {
        const val GET_MEALS = "search.php?s"
        const val GET_MEALS_BY_CATEGORY = "filter.php"
        const val GET_CATEGORIES = "categories.php"
        const val GET_MEAL_RECIPE = "lookup.php"
    }
}