package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.repository.MealsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMealUpdatesUseCase @Inject constructor(
    private val mealsRepository: MealsRepository
) {
    suspend fun getMealUpdates() = mealsRepository.getDishUpdates()
}