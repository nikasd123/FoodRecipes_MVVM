package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.models.DishesResponse
import com.tz.fooddelivery.domain.repository.DishesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDishesUseCase @Inject constructor(
    private val dishesRepository: DishesRepository
){
    suspend fun getDishes(): DishesResponse = dishesRepository.getDishes()
    suspend fun getDishesByCategory(category: String): DishesResponse = dishesRepository.getDishesByCategory(category)
}