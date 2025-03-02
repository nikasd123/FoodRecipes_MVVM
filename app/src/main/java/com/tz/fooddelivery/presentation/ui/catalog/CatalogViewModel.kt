package com.tz.fooddelivery.presentation.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealsUseCase
import com.tz.fooddelivery.presentation.common.mapError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase
) : ViewModel() {

    private val _categoriesState = MutableStateFlow<CategoriesState>(CategoriesState.Loading)
    val categoriesState: StateFlow<CategoriesState> = _categoriesState.asStateFlow()

    private val _dishesState = MutableStateFlow<DishesState>(DishesState.Loading)
    val dishesState: StateFlow<DishesState> = _dishesState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    init {
        loadInitialData()
    }

    fun selectCategory(category: Category?) {
        _selectedCategory.value = category
        category?.let { loadDishesByCategory(it) } ?: loadDishes()
    }

    fun retry() {
        when (val current = _dishesState.value) {
            is DishesState.Error -> current.lastCategory?.let { loadDishesByCategory(it) }
            else -> loadInitialData()
        }
    }

    private fun loadInitialData() {
        loadCategories()
        loadDishes()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoriesState.value = CategoriesState.Loading
            getCategoriesUseCase.getCategories()
                .catch { e ->
                    _categoriesState.value = CategoriesState.Error(
                        error = mapError(e),
                        message = "Failed to load categories"
                    )
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _categoriesState.value = CategoriesState.Success(result.data)
                        }
                        is Result.Error -> {
                            _categoriesState.value = CategoriesState.Error(
                                error = result.error,
                                message = "Categories loading failed"
                            )
                        }
                    }
                }
        }
    }

    private fun loadDishes() {
        viewModelScope.launch {
            _dishesState.value = DishesState.Loading
            getMealsUseCase.getDishes()
                .catch { e ->
                    _dishesState.value = DishesState.Error(
                        error = mapError(e),
                        message = "Failed to load dishes",
                        lastCategory = null
                    )
                }
                .collect { result ->
                    handleDishResult(result, null)
                }
        }
    }

    private fun loadDishesByCategory(category: Category) {
        viewModelScope.launch {
            _dishesState.value = DishesState.Loading
            getMealsUseCase.getDishesByCategory(category.originalName)
                .catch { e ->
                    _dishesState.value = DishesState.Error(
                        error = mapError(e),
                        message = "Failed to load dishes",
                        lastCategory = category
                    )
                }
                .collect { result ->
                    handleDishResult(result, category)
                }
        }
    }

    private fun handleDishResult(result: Result<DishItem, NetworkError>, category: Category?) {
        when (result) {
            is Result.Success -> {
                val newList = (_dishesState.value as? DishesState.Success)
                    ?.dishes
                    ?.toMutableList()
                    ?: mutableListOf()

                if (!newList.any { it.id == result.data.id }) {
                    newList.add(result.data)
                    _dishesState.value = DishesState.Success(
                        dishes = newList,
                        category = category ?: _selectedCategory.value
                    )
                }
            }
            is Result.Error -> {
                _dishesState.value = DishesState.Error(
                    error = result.error,
                    message = "Dish loading error",
                    lastCategory = category
                )
            }
        }
    }
}