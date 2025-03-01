package com.tz.fooddelivery.presentation.ui.catalog

import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.models.Category

sealed interface CategoriesState {
    data object Loading : CategoriesState
    data class Success(val categories: List<Category>) : CategoriesState
    data class Error(val error: NetworkError, val message: String) : CategoriesState
}