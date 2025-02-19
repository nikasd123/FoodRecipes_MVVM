package com.tz.fooddelivery.data.repository

import android.util.Log
import com.tz.fooddelivery.data.local.dao.CategoriesDao
import com.tz.fooddelivery.data.local.dao.DishesDao
import com.tz.fooddelivery.data.local.entities.CategoryEntity
import com.tz.fooddelivery.data.local.entities.DishEntity
import com.tz.fooddelivery.data.local.entities.toDomain
import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.data.remote.dto.CategoryDto
import com.tz.fooddelivery.data.remote.dto.DishItemDto
import com.tz.fooddelivery.data.remote.dto.toDomain
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.models.toEntity
import com.tz.fooddelivery.domain.repository.MealsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val mealsApi: MealsApi,
    private val dishesDao: DishesDao,
    private val categoriesDao: CategoriesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MealsRepository {

    override suspend fun getDishes(): List<DishItem>? = withContext(ioDispatcher) {
        handleNetworkOperation(
            networkCall = { mealsApi.getMeals().meals?.toDishItems() },
            cacheOperation = { dishesDao.getAllDishes().toDishItemsFromEntity() },
            insertCache = { dishes -> dishesDao.insertAll(dishes.toDishEntities()) }
        )
    }

    override suspend fun getCategories(): List<Category>? = withContext(ioDispatcher) {
        handleNetworkOperation(
            networkCall = { mealsApi.getCategories().categories?.toCategories() },
            cacheOperation = { categoriesDao.getAllCategories().toCategoriesFromEntity() },
            insertCache = { categories -> categoriesDao.insertAll(categories.toCategoryEntities()) }
        )
    }

    override suspend fun getDishesByCategory(category: String): List<DishItem>? = withContext(ioDispatcher) {
        handleNetworkOperation(
            networkCall = { mealsApi.getMealsByCategory(category).meals?.toDishItems() },
            cacheOperation = { dishesDao.getDishesByCategory(category).toDishItemsFromEntity() },
            insertCache = { dishes -> dishesDao.insertAll(dishes.toDishEntities()) }
        )
    }

    private suspend fun <T : Any> handleNetworkOperation(
        networkCall: suspend () -> List<T>?,
        cacheOperation: suspend () -> List<T>,
        insertCache: suspend (List<T>) -> Unit
    ): List<T>? = try {
        val networkData = networkCall()
        if (networkData != null) {
            coroutineScope {
                launch(ioDispatcher) {
                    try {
                        insertCache(networkData)
                    } catch (e: Exception) {
                        Log.e("Repository", "Cache insert failed", e)
                    }
                }
                networkData
            }
        } else {
            cacheOperation().takeIf { it.isNotEmpty() }
        }
    } catch (e: Exception) {
        Log.e("Repository", "Network call failed", e)
        cacheOperation().takeIf { it.isNotEmpty() }
    }

    // Extension functions for conversions
    private fun List<DishItemDto>.toDishItems() = map { it.toDomain() }
    private fun List<CategoryDto>.toCategories() = map { it.toDomain() }
    private fun List<CategoryEntity>.toCategoriesFromEntity() = map { it.toDomain() }
    private fun List<DishEntity>.toDishItemsFromEntity() = map { it.toDomain() }
    private fun List<DishItem>.toDishEntities() = map { it.toEntity() }
    private fun List<Category>.toCategoryEntities() = map { it.toEntity() }
}