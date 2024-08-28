package com.kaz4.data.repository

import com.kaz4.data.local.dao.MealsDao
import com.kaz4.data.local.entities.toDomain
import com.kaz4.data.local.entities.toEntity
import com.kaz4.data.remote.api.MealsApi
import com.kaz4.data.remote.dto.toDomain
import com.kaz4.domain.models.Category
import com.kaz4.domain.models.MealItem
import com.kaz4.domain.repository.MealsRepository
import com.tz.fooddelivery.data.local.dao.CategoriesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealsRepositoryImpl(
    private val mealsApi: MealsApi,
    private val mealsDao: MealsDao,
    private val categoriesDao: CategoriesDao
): MealsRepository {
    override suspend fun getMeals(): List<MealItem>? = withContext(Dispatchers.IO){
        return@withContext try {
            val dishesFromApi = mealsApi.getMeals().meals?.map { it.toDomain() }
            mealsDao.insertAll(dishesFromApi?.map { it.toEntity() } ?: emptyList())
            dishesFromApi
        } catch (e: Exception){
            mealsDao.getAllDishes().map { it.toDomain() }
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

    override suspend fun getMealsByCategory(category: String): List<MealItem>? = withContext(Dispatchers.IO){
        try {
            val dishesFromApi = mealsApi.getMealsByCategory(category).meals?.map { it.toDomain() }
            mealsDao.insertAll(dishesFromApi?.map { it.toEntity() } ?: emptyList())
            dishesFromApi
        } catch (e: Exception){
            mealsDao.getDishesByCategory(category).map { it.toDomain() }
        }
    }

}