package com.tz.fooddelivery.presentation.ui.catalog

import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem

sealed interface FavoriteDishesState {
    data object Loading: FavoriteDishesState
    data class Success(
        val favoriteDishes: List<DishItem>
    ) : FavoriteDishesState
    data class Error(
        val error: NetworkError,
        val message: String
    ) : FavoriteDishesState
}