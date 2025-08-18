package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.local.data_source.LocalDataSource
import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): CategoryRepository{
    override suspend fun getCategories(): Flow<Result<List<Category>, DataError>> = flow {
        try {
            val categories = withContext(ioDispatcher) {
                localDataSource.getCategories()
            }
            emit(Result.Success(categories))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Local.DATABASE_ERROR))
        }
    }
}