package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.repository.MealsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetFavoriteDishUseCase @Inject constructor(
    private val mealsRepository: MealsRepository
)  {
    suspend fun setFavoriteDish(dishId : String) : Boolean {
        return mealsRepository.setFavoriteDish(dishId = dishId)
    }
}