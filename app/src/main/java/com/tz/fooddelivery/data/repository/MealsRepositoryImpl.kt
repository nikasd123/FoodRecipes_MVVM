package com.tz.fooddelivery.data.repository

import android.util.Log
import com.tz.fooddelivery.data.local.dao.CategoriesDao
import com.tz.fooddelivery.data.local.dao.DishesDao
import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.common.mappers.mapError
import com.tz.fooddelivery.domain.common.mappers.mapLocalError
import com.tz.fooddelivery.domain.common.mappers.toCategories
import com.tz.fooddelivery.domain.common.mappers.toCategoriesFromEntity
import com.tz.fooddelivery.domain.common.mappers.toCategoryEntities
import com.tz.fooddelivery.domain.common.mappers.toDishEntities
import com.tz.fooddelivery.domain.common.mappers.toDishItems
import com.tz.fooddelivery.domain.common.mappers.toDishItemsFromEntity
import com.tz.fooddelivery.domain.repository.MealsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val mealsApi: MealsApi,
    private val dishesDao: DishesDao,
    private val categoriesDao: CategoriesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MealsRepository {

    override suspend fun getDishes() = handleNetworkOperation(
        networkCall = { mealsApi.getMeals().meals?.toDishItems() },
        cacheReader = { dishesDao.getAllDishes().toDishItemsFromEntity() },
        cacheWriter = { dishes -> dishesDao.insertAll(dishes.toDishEntities()) }
    )

    override suspend fun getCategories() = handleNetworkOperation(
        networkCall = { mealsApi.getCategories().categories?.toCategories() },
        cacheReader = { categoriesDao.getAllCategories().toCategoriesFromEntity() },
        cacheWriter = { categories -> categoriesDao.insertAll(categories.toCategoryEntities()) }
    )

    override suspend fun getDishesByCategory(category: String) = handleNetworkOperation(
        networkCall = { mealsApi.getMealsByCategory(category).meals?.toDishItems() },
        cacheReader = { dishesDao.getDishesByCategory(category).toDishItemsFromEntity() },
        cacheWriter = { dishes -> dishesDao.insertAll(dishes.toDishEntities()) }
    )

    private suspend fun <T : Any> handleNetworkOperation(
        networkCall: suspend () -> List<T>?,
        cacheReader: suspend () -> List<T>,
        cacheWriter: suspend (List<T>) -> Unit
    ): Result<List<T>, DataError> = withContext(ioDispatcher) {
        try {
            processNetworkCall(networkCall, cacheWriter)
        } catch (e: Exception) {
            processCacheFallback(e, cacheReader)
        }
    }

    private suspend fun <T> processNetworkCall(
        networkCall: suspend () -> List<T>?,
        cacheWriter: suspend (List<T>) -> Unit
    ): Result<List<T>, DataError> {
        return networkCall()?.let { data ->
            cacheWriter.asyncWithErrorHandling(data)
            Result.Success(data)
        } ?: Result.Error(DataError.Network.DATA_NOT_FOUND)
    }

    private suspend fun <T : Any> processCacheFallback(
        exception: Exception,
        cacheReader: suspend () -> List<T>
    ): Result<List<T>, DataError> {
        Log.e("Repository", "Network error", exception)
        return try {
            cacheReader().takeIf { it.isNotEmpty() }?.let {
                Result.Success(it)
            } ?: Result.Error(mapError(exception))
        } catch (e: Exception) {
            Result.Error(mapLocalError(e))
        }
    }

    private suspend fun <T> (suspend (T) -> Unit).asyncWithErrorHandling(data: T) {
        withContext(ioDispatcher) {
            try {
                invoke(data)
            } catch (e: Exception) {
                Log.e("Repository", "Cache write failed", e)
            }
        }
    }
}