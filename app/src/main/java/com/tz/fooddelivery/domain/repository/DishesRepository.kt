package com.tz.fooddelivery.domain.repository

import com.tz.fooddelivery.domain.models.CategoriesResponse
import com.tz.fooddelivery.domain.models.DishesResponse

interface DishesRepository {
    suspend fun getDishes(): DishesResponse
    suspend fun getCategories(): CategoriesResponse
    suspend fun getDishesByCategory(category: String): DishesResponse
}