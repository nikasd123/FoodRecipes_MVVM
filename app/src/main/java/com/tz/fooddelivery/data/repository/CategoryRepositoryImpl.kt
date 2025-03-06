package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.local.dao.CategoriesDao
import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.common.mappers.mapError
import com.tz.fooddelivery.domain.common.mappers.toCategories
import com.tz.fooddelivery.domain.common.mappers.toCategoriesFromEntity
import com.tz.fooddelivery.domain.common.mappers.toCategoryEntities
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val mealsApi: MealsApi,
    private val categoriesDao: CategoriesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): CategoryRepository{
    override suspend fun getCategories(): Flow<Result<List<Category>, DataError>> =
        fetchData(
            getCache = { categoriesDao.getAllCategories().toCategoriesFromEntity() },
            getNetwork = { mealsApi.getCategories().categories?.toCategories() ?: emptyList() },
            saveCache = { networkData -> categoriesDao.insertAll(networkData.toCategoryEntities()) }
        )

    private fun <T> fetchData(
        getCache: suspend () -> List<T>,
        getNetwork: suspend () -> List<T>,
        saveCache: suspend (List<T>) -> Unit
    ): Flow<Result<List<T>, DataError>> = channelFlow {
        val cachedData = withContext(ioDispatcher) { getCache() }
        if (cachedData.isNotEmpty()) {
            send(Result.Success(cachedData))
        }
        try {
            val networkData = getNetwork()

            withContext(ioDispatcher) { saveCache(networkData) }
            send(Result.Success(networkData))
        } catch (e: Exception) {
            send(Result.Error(mapError(e)))
        }
    }
}