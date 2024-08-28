package com.kaz4.domain.repository

import com.kaz4.domain.models.Category
import com.kaz4.domain.models.MealItem

interface MealsRepository {
    suspend fun getMeals(): List<MealItem>?
    suspend fun getCategories(): List<Category>?
    suspend fun getMealsByCategory(category: String): List<MealItem>?
}