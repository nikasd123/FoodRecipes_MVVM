package com.tz.fooddelivery.presentation.ui.catalog

import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem

sealed interface DishesState {
    data object Loading : DishesState
    data class Success(
        val dishes: List<DishItem>
    ) : DishesState
    data class Error(
        val error: NetworkError,
        val message: String,
        val lastCategory: Category?
    ) : DishesState
}