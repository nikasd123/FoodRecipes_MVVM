package com.tz.fooddelivery.domain.repository

import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem

interface DishesRepository {
    suspend fun getDishes(): List<DishItem>?
    suspend fun getCategories(): List<Category>?
    suspend fun getDishesByCategory(category: String): List<DishItem>?
}