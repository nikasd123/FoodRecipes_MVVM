package com.tz.fooddelivery.domain.repository

import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.MealItem

interface MealsRepository {
    suspend fun getMeals(): List<MealItem>?
    suspend fun getCategories(): List<Category>?
    suspend fun getMealsByCategory(category: String): List<MealItem>?
}