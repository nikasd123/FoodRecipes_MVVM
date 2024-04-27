package com.tz.fooddelivery.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.MealItem
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase
): ViewModel() {
    private val _dishesList = MutableLiveData<List<MealItem>?>()
    val dishesList: LiveData<List<MealItem>?> = _dishesList

    private val _categoriesList = MutableLiveData<List<Category>?>()
    val categoriesList: LiveData<List<Category>?> = _categoriesList

    private fun getDishes(){
        viewModelScope.launch {
            val response = getMealsUseCase.getDishes()
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
            val dishes = getMealsUseCase.getDishesByCategory(category)
            _dishesList.value = dishes
        }
    }

    init {
        getDishes()
        getCategories()
    }
}