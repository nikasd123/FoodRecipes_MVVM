package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.local.data_source.LocalDataSource
import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.MealRecipe
import com.tz.fooddelivery.domain.repository.MealRecipeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealRecipeRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): MealRecipeRepository {

    override suspend fun getDishById(id: String): Flow<Result<MealRecipe, DataError>> = flow {
        try {
            val recipe = withContext(ioDispatcher) {
                localDataSource.getDishById(id)
            }
            recipe?.let {
                emit(Result.Success(it))
            } ?: emit(Result.Error(DataError.Local.DATABASE_ERROR))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Local.DATABASE_ERROR))
        }
    }
}