package com.tz.fooddelivery.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.models.CategoriesResponse
import com.tz.fooddelivery.domain.models.DishesResponse
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetDishesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getDishesUseCase: GetDishesUseCase
): ViewModel() {
    private val _dishesList = MutableLiveData<DishesResponse>()
    val dishesList: LiveData<DishesResponse> = _dishesList

    private val _categoriesList = MutableLiveData<CategoriesResponse>()
    val categoriesList: LiveData<CategoriesResponse> = _categoriesList

    private fun getDishes(){
        viewModelScope.launch {
            val response = getDishesUseCase.getDishes()
            _dishesList.value = response
        }
    }

    private fun getCategories(){
        viewModelScope.launch {
            val response = getCategoriesUseCase.getCategories()
            _categoriesList.value = response
        }
    }

    fun getDishesByCategory(category: String) {
        viewModelScope.launch {
            val dishes = getDishesUseCase.getDishesByCategory(category)
            _dishesList.value = dishes
        }
    }

    init {
        getDishes()
        getCategories()
    }
}