package com.tz.fooddelivery.data.remote.api

interface MealsApi {
//    @GET(GET_MEALS)
//    suspend fun getMeals(): MealsResponseDto
//
//    @GET(GET_MEALS_BY_CATEGORY)
//    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponseDto
//
//    @GET(GET_CATEGORIES)
//    suspend fun getCategories(): CategoriesResponseDto
//
//    @POST(GET_MEAL_RECIPE)
//    suspend fun getMealRecipeById(@Query("i") id: String): MealsRecipeResponseDto

    companion object {
        private const val GET_MEALS = "search.php?s"
        private const val GET_MEALS_BY_CATEGORY = "filter.php"
        private const val GET_CATEGORIES = "categories.php"
        private const val GET_MEAL_RECIPE = "lookup.php"
    }
}