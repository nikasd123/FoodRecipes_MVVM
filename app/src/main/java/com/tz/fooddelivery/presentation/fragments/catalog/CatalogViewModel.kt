package com.tz.fooddelivery.presentation.fragments.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    init {
        loadInitialData()
    }

    fun selectCategory(category: Category) {
        _selectedCategory.value = category
        loadDishesByCategory(category)
    }

    fun retry() {
        _selectedCategory.value?.let { loadDishesByCategory(it) } ?: loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val categoriesResult = handleResult { getCategoriesUseCase.getCategories() }
            val dishesResult = handleResult { getMealsUseCase.getDishes() }

            _uiState.value = when {
                categoriesResult is Result.Success && dishesResult is Result.Success -> {
                    CatalogUiState.Success(
                        categories = categoriesResult.data,
                        dishes = dishesResult.data,
                        selectedCategory = null
                    )
                }
                categoriesResult is Result.Error || dishesResult is Result.Error -> {
                    val error = (categoriesResult as? Result.Error)?.error ?: (dishesResult as? Result.Error)?.error
                    CatalogUiState.Error(
                        error = error ?: NetworkError.UNKNOWN_ERROR,
                        message = "Failed to load data",
                        lastCategory = null
                    )
                }
                else -> CatalogUiState.Loading
            }
        }
    }


    private fun loadDishesByCategory(category: Category) {
        viewModelScope.launch {
            val result = handleResult { getMealsUseCase.getDishesByCategory(category.originalName) }
            _uiState.value = when (result) {
                is Result.Success -> {
                    val currentState = _uiState.value as? CatalogUiState.Success
                    currentState?.copy(
                        dishes = result.data,
                        selectedCategory = category
                    ) ?: CatalogUiState.Success(
                        categories = emptyList(),
                        dishes = result.data,
                        selectedCategory = category
                    )
                }

                is Result.Error -> {
                    CatalogUiState.Error(
                        error = result.error,
                        message = "Failed to load dishes",
                        lastCategory = category
                    )
                }
            }
        }
    }

    private suspend fun <T> handleResult(
        block: suspend () -> Flow<Result<T, NetworkError>>
    ): Result<T, NetworkError> {
        return try {
            block().first()
        } catch (e: Exception) {
            Result.Error(mapError(e))
        }
    }

    private fun mapError(e: Exception): NetworkError =
        when (e) {
            is IOException -> NetworkError.NETWORK_ERROR
            is NullPointerException -> NetworkError.DATA_NOT_FOUND
            else -> NetworkError.UNKNOWN_ERROR
        }

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
}
