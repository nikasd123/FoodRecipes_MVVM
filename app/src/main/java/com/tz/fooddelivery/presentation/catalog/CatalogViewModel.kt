package com.tz.fooddelivery.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealsUseCase
import com.tz.fooddelivery.domain.common.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias PairMealsAndCategoryList = Pair<List<DishItem>, List<Category>>

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase
) : ViewModel() {
    private val _dishesState = MutableLiveData<State<List<DishItem>>>()
    val dishesState: LiveData<State<List<DishItem>>> = _dishesState

    private val _categoriesList = MutableLiveData<List<Category>?>()
    val categoriesList: LiveData<List<Category>?> = _categoriesList

    private val _state = MutableStateFlow<State<PairMealsAndCategoryList>>(State.Loading)
    val state: StateFlow<State<PairMealsAndCategoryList>> = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val dishes = getMealsUseCase.getDishes()
                val categories = getCategoriesUseCase.getCategories()
                _state.value = State.Success(
                    Pair(first = dishes ?: emptyList(), second = categories ?: emptyList())
                )
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getDishesByCategory(category: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val dishes = getMealsUseCase.getDishesByCategory(category) ?: emptyList()
                _dishesState.value = State.Success(dishes)
            } catch (e: Exception){
                _state.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }
}