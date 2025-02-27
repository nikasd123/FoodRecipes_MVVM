package com.tz.fooddelivery.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MealsUiState>(MealsUiState.Loading)
    val uiState: StateFlow<MealsUiState> = _uiState.asStateFlow()

    private val _categoriesState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Loading)
    val categoriesState: StateFlow<CategoriesUiState> = _categoriesState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        loadCategories()
        loadDishes()
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        loadDishesByCategory(category)
    }

    fun retry() {
        when (val currentState = _uiState.value) {
            is MealsUiState.Error -> {
                currentState.lastCategory?.let { loadDishesByCategory(it) }
                    ?: loadDishes()
            }
            else -> loadInitialData()
        }
    }

    private fun loadDishes() {
        viewModelScope.launch {
            getMealsUseCase.getDishes()
                .catch { e -> handleDishesError(e, null) }
                .collect { handleDishesResult(it) }
        }
    }

    private fun loadDishesByCategory(category: String) {
        viewModelScope.launch {
            getMealsUseCase.getDishesByCategory(category)
                .catch { e -> handleDishesError(e, category) }
                .collect { handleDishesResult(it) }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.getCategories()
                .catch { e ->
                    val error = when (e) {
                        is IOException -> NetworkError.NETWORK_ERROR
                        else -> NetworkError.UNKNOWN_ERROR
                    }
                    _categoriesState.value = CategoriesUiState.Error(
                        error = error,
                        message = "Failed to load categories"
                    )
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _categoriesState.value = CategoriesUiState.Success(result.data)
                        }
                        is Result.Error -> {
                            _categoriesState.value = CategoriesUiState.Error(
                                error = result.error,
                                message = "Categories loading failed"
                            )
                        }
                    }
                }
        }
    }

    private fun handleDishesResult(result: Result<List<DishItem>, NetworkError>) {
        when (result) {
            is Result.Success -> {
                _uiState.value = MealsUiState.Success(
                    dishes = result.data,
                    category = _selectedCategory.value
                )
            }
            is Result.Error -> {
                _uiState.value = MealsUiState.Error(
                    error = result.error,
                    message = "Dishes loading failed",
                    lastCategory = _selectedCategory.value
                )
            }
        }
    }

    private fun handleDishesError(e: Throwable, category: String?) {
        val error = when (e) {
            is IOException -> NetworkError.NETWORK_ERROR
            is NullPointerException -> NetworkError.DATA_NOT_FOUND
            else -> NetworkError.UNKNOWN_ERROR
        }

        _uiState.value = MealsUiState.Error(
            error = error,
            message = e.message ?: "Unknown error",
            lastCategory = category
        )
    }

    sealed interface MealsUiState {
        data object Loading : MealsUiState
        data class Success(
            val dishes: List<DishItem>,
            val category: String?
        ) : MealsUiState

        data class Error(
            val error: NetworkError,
            val message: String,
            val lastCategory: String?
        ) : MealsUiState
    }

    sealed interface CategoriesUiState {
        data object Loading : CategoriesUiState
        data class Success(val categories: List<Category>) : CategoriesUiState
        data class Error(
            val error: NetworkError,
            val message: String
        ) : CategoriesUiState
    }
}
