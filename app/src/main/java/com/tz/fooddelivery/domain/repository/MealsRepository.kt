package com.tz.fooddelivery.domain.repository

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem

interface MealsRepository {
    suspend fun getDishes(): Result<List<DishItem>, DataError>
    suspend fun getCategories(): Result<List<Category>, DataError>
    suspend fun getDishesByCategory(category: String): Result<List<DishItem>, DataError>
}