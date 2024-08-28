package com.kaz4.foodrecipescompose.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaz4.domain.models.Category
import com.kaz4.domain.models.MealItem
import com.kaz4.domain.use_cases.GetCategoriesUseCase
import com.kaz4.domain.use_cases.GetMealsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase
): ViewModel() {
    private val _categories = MutableStateFlow<List<Category>?>(emptyList())
    val categories: StateFlow<List<Category>?> = _categories.asStateFlow()

    private val _meals = MutableStateFlow<List<MealItem>?>(emptyList())
    val meals: StateFlow<List<MealItem>?> = _meals.asStateFlow()

    init {
        getCategories()
        getMeals()
    }

    private fun getCategories() {
        viewModelScope.launch {
            val response = getCategoriesUseCase.getCategories()
            _categories.value = response
        }
    }

    private fun getMeals() {
        viewModelScope.launch {
            val response = getMealsUseCase.getMeals()
            _meals.value = response
        }
    }
}