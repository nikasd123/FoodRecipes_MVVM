package com.tz.fooddelivery.domain.repository

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(): Flow<Result<List<Category>, DataError>>
}