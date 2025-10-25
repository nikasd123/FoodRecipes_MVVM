package com.tz.fooddelivery.domain.repository

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.DishItem
import kotlinx.coroutines.flow.Flow

interface MealsRepository {
    suspend fun getDishes(): Flow<Result<List<DishItem>, DataError>>
    suspend fun getDishesByCategory(category: String): Flow<Result<List<DishItem>, DataError>>
    suspend fun getFavoriteDishes(): Flow<Result<List<DishItem>, DataError>>
    suspend fun setFavoriteDish(dishId: String) : Boolean
    fun getDishUpdates(): Flow<Unit>
}