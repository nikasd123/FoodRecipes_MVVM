package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.repository.MealsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMealsUseCase @Inject constructor(
    private val mealsRepository: MealsRepository
){
    suspend fun getDishes(): List<DishItem>? = mealsRepository.getDishes()
    suspend fun getDishesByCategory(category: String): List<DishItem>? = mealsRepository.getDishesByCategory(category)
}