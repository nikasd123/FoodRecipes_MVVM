package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.local.CategoriesDao
import com.tz.fooddelivery.data.local.DishesDao
import com.tz.fooddelivery.data.local.toDomain
import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.data.remote.dto.toDomain
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.models.toEntity
import com.tz.fooddelivery.domain.repository.DishesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DishesRepositoryImpl @Inject constructor(
    private val mealsApi: MealsApi,
    private val dishesDao: DishesDao,
    private val categoriesDao: CategoriesDao
): DishesRepository {
    override suspend fun getDishes(): List<DishItem>? = withContext(Dispatchers.IO){
        return@withContext try {
            val dishesFromApi = mealsApi.getMeals().meals?.map { it.toDomain() }
            dishesDao.insertAll(dishesFromApi?.map { it.toEntity() } ?: emptyList())
            dishesFromApi
        } catch (e: Exception){
            dishesDao.getAllDishes().map { it.toDomain() }
        }
    }

    override suspend fun getCategories(): List<Category>? = withContext(Dispatchers.IO){
        try {
            val categoriesFromApi = mealsApi.getCategories().categories?.map { it.toDomain() }
            categoriesDao.insertAll(categoriesFromApi?.map { it.toEntity() } ?: emptyList())
            categoriesFromApi
        } catch (e: Exception){
            categoriesDao.getAllCategories().map { it.toDomain() }
        }
    }


    override suspend fun getDishesByCategory(category: String): List<DishItem>? = withContext(Dispatchers.IO){
        try {
            val dishesFromApi = mealsApi.getMealsByCategory(category).meals?.map { it.toDomain() }
            dishesDao.insertAll(dishesFromApi?.map { it.toEntity() } ?: emptyList())
            dishesFromApi
        } catch (e: Exception){
            dishesDao.getDishesByCategory(category).map { it.toDomain() }
        }
    }

}