package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.local.dao.DishesDao
import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.common.mappers.mapError
import com.tz.fooddelivery.domain.common.mappers.toDishEntities
import com.tz.fooddelivery.domain.common.mappers.toDishItems
import com.tz.fooddelivery.domain.common.mappers.toDishItemsFromEntity
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.repository.MealsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val mealsApi: MealsApi,
    private val dishesDao: DishesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MealsRepository {

    override suspend fun getDishes(): Flow<Result<List<DishItem>, DataError>> =
        fetchData(
            getCache = {
                dishesDao.getAllDishes().toDishItemsFromEntity()
            },
            getNetwork = { mealsApi.getMeals().meals?.toDishItems() ?: emptyList() },
            saveCache = { networkData -> dishesDao.insertAll(networkData.toDishEntities()) }
        )

    override suspend fun getDishesByCategory(category: String): Flow<Result<List<DishItem>, DataError>> =
        fetchData(
            getCache = { dishesDao.getDishesByCategory(category).toDishItemsFromEntity() },
            getNetwork = {
                mealsApi.getMealsByCategory(category).meals?.toDishItems() ?: emptyList()
            },
            saveCache = { networkData ->
                if (networkData.isNotEmpty()) {
                    dishesDao.clearByCategory(category)
                    val fixedData = networkData.map { it.copy(category = category) }
                    dishesDao.insertAll(fixedData.toDishEntities())
                }
            }
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

    override suspend fun setFavoriteDish(dishId: String): Boolean = withContext(ioDispatcher) {
        val response = dishesDao.toggleFavoriteDish(dishId)

        return@withContext response == 1
    }

    override suspend fun getFavoriteDishes(): Flow<Result<List<DishItem>, DataError>> = flow {
        try {
            val dishes = withContext(ioDispatcher) {
                dishesDao.getFavoriteDishes()
            }
            emit(Result.Success(dishes.toDishItemsFromEntity()))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Local.DATABASE_ERROR))
        }
    }
}