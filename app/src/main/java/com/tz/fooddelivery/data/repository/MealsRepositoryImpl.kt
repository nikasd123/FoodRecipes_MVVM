package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.local.data_source.LocalDataSource
import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.repository.MealsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MealsRepository {

   // override suspend fun getDishes(): Flow<Result<List<DishItem>, DataError>> {
   //
   // }

    override suspend fun getDishesByCategory(category: String): Flow<Result<List<DishItem>, DataError>> = flow {
        try {
            val dishes = withContext(ioDispatcher) {
                localDataSource.getDishesByCategory(category)
            }
            emit(Result.Success(dishes))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Local.DATABASE_ERROR))
        }
    }
}