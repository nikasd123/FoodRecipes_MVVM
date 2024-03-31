package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.repository.DishesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(
    private val dishesRepository: DishesRepository
){
    suspend fun getCategories(): List<Category>? = dishesRepository.getCategories()
}