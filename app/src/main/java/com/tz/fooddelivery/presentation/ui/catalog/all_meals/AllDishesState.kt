package com.tz.fooddelivery.presentation.ui.catalog.all_meals

import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.models.DishItem

sealed interface AllDishesState {
    data object Loading: AllDishesState
    data class Success(
        val allDishes: List<DishItem>
    ) : AllDishesState
    data class Error(
        val error: NetworkError,
        val message: String
    ): AllDishesState

}