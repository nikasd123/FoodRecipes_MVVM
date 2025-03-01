package com.tz.fooddelivery.presentation.ui.catalog

import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem

sealed interface CatalogUiState {
    data object Loading : CatalogUiState
    data class Success(
        val categories: List<Category>,
        val dishes: List<DishItem>,
        val selectedCategory: Category?
    ) : CatalogUiState

    data class Error(
        val error: NetworkError,
        val message: String,
        val lastCategory: Category?
    ) : CatalogUiState
}