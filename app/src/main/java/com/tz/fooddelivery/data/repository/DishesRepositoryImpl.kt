package com.tz.fooddelivery.data.repository

import com.tz.fooddelivery.data.api.MealsApi
import com.tz.fooddelivery.data.dto.toDomain
import com.tz.fooddelivery.domain.models.CategoriesResponse
import com.tz.fooddelivery.domain.models.DishesResponse
import com.tz.fooddelivery.domain.repository.DishesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DishesRepositoryImpl @Inject constructor(
    private val mealsApi: MealsApi
): DishesRepository {
    override suspend fun getDishes(): DishesResponse = withContext(Dispatchers.IO){
        mealsApi.getMeals().toDomain()
    }

    override suspend fun getCategories(): CategoriesResponse = withContext(Dispatchers.IO){
        mealsApi.getCategories().toDomain()
    }

    override suspend fun getDishesByCategory(category: String): DishesResponse = withContext(Dispatchers.IO){
        mealsApi.getMealsByCategory(category).toDomain()
    }
}