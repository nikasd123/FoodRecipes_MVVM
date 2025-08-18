package com.tz.fooddelivery.presentation.ui.catalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DefaultCategory
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetFavoriteDishesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealsUseCase
import com.tz.fooddelivery.domain.use_cases.SetFavoriteDishUseCase
import com.tz.fooddelivery.presentation.common.mapError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase,
    private val setFavoriteDishUseCase: SetFavoriteDishUseCase,
    private val getFavoriteDishesUseCase: GetFavoriteDishesUseCase
) : ViewModel() {

    private val _categoriesState = MutableStateFlow<CategoriesState>(CategoriesState.Loading)
    val categoriesState: StateFlow<CategoriesState> = _categoriesState.asStateFlow()

    private val _dishesState = MutableStateFlow<DishesState>(DishesState.Loading)
    val dishesState: StateFlow<DishesState> = _dishesState.asStateFlow()

    private val _favoriteDishesState = MutableStateFlow<FavoriteDishesState>(FavoriteDishesState.Loading)
    val favoriteDishesState: StateFlow<FavoriteDishesState> = _favoriteDishesState.asStateFlow()

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
        loadFavoriteDishes()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoriesState.value = CategoriesState.Loading
            getCategoriesUseCase.getCategories()
                .catch { e ->
                    Log.e("CatalogVM", "Categories load error", e)
                    _categoriesState.value = CategoriesState.Error(
                        error = mapError(e),
                        message = "Failed to load categories"
                    )
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            if (result.data.isNotEmpty()) {
                                _categoriesState.value = CategoriesState.Success(result.data)
                            } else {
                                _categoriesState.value = CategoriesState.Error(
                                    error = NetworkError.DATA_NOT_FOUND,
                                    message = "No categories found"
                                )
                            }
                        }
                        is Result.Error -> {
                            _categoriesState.value = CategoriesState.Error(
                                error = result.error,
                                message = when (result.error) {
                                    NetworkError.DATA_NOT_FOUND -> "Categories not found"
                                    else -> "Failed to load categories"
                                }
                            )
                        }
                    }
                }
        }
    }

    private fun loadFavoriteDishes(){
        viewModelScope.launch {
            _favoriteDishesState.value = FavoriteDishesState.Loading
            getFavoriteDishesUseCase.getFavoriteDishes()
                .catch { e ->
                    _favoriteDishesState.value = FavoriteDishesState.Error(
                        error = mapError(e),
                        message = "Failed to load favorite dishes"
                    )
                }
                .collect{ result ->
                    when(result){
                        is Result.Success -> {
                            _favoriteDishesState.value = FavoriteDishesState.Success(result.data)

                            val currentDishesState = _dishesState.value

                            if (currentDishesState is DishesState.Success && result.data.isNotEmpty()) {

                                val allDishes = currentDishesState.dishes
                                val favoriteDishes = result.data

                                val favoriteDishIds = favoriteDishes.map { it.id }.toSet()

                                val updatedAllDishes = allDishes.map { dish ->
                                    if (dish.id in favoriteDishIds) {
                                        dish.copy(isFavorite = true)
                                    } else {
                                        dish.copy(isFavorite = false)
                                    }
                                }

                                _dishesState.value = DishesState.Success(updatedAllDishes)
                            }
                        }
                        is Result.Error -> {
                            _favoriteDishesState.value = FavoriteDishesState.Error(
                                error = result.error,
                                message = "Failed to load favorite dishes"
                            )
                        }
                    }
                }
        }
    }

    private fun loadDishes() {
        viewModelScope.launch {
            _dishesState.value = DishesState.Loading
            getMealsUseCase.getDishesByCategory("beef")
                .catch { e ->
                    _dishesState.value = DishesState.Error(
                        error = mapError(e),
                        message = "Failed to load dishes",
                        lastCategory = DefaultCategory
                    )
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _dishesState.value = DishesState.Success(result.data)
                        }
                        is Result.Error -> {
                            _dishesState.value = DishesState.Error(
                                error = result.error,
                                message = "Failed to load dishes",
                                lastCategory = DefaultCategory
                            )
                        }
                    }
                    //handleDishResult(result, null)
                }
        }
    }

    private fun loadDishesByCategory(category: Category) {
        viewModelScope.launch {
            _dishesState.value = DishesState.Loading
            getMealsUseCase.getDishesByCategory(category.category.lowercase(Locale.ROOT))
                .catch { e ->
                    _dishesState.value = DishesState.Error(
                        error = mapError(e),
                        message = "Failed to load dishes",
                        lastCategory = category
                    )
                }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _dishesState.value = DishesState.Success(result.data)
                        }
                        is Result.Error -> {
                            _dishesState.value = DishesState.Error(
                                error = result.error,
                                message = "Failed to load dishes",
                                lastCategory = category
                            )
                        }
                    }
                    //handleDishResult(result, category)
                }
        }
    }

    fun handleFavoriteButtonClick(dishItem: DishItem) {

        viewModelScope.launch {

            val success = setFavoriteDishUseCase.setFavoriteDish(dishId = dishItem.id)

            if(success){
                loadFavoriteDishes()
            }
        }

    }
}