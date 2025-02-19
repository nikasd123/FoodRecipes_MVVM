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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias PairMealsAndCategoryList = Pair<List<DishItem>, List<Category>>

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State<PairMealsAndCategoryList>>(State.Loading)
    val state: StateFlow<State<PairMealsAndCategoryList>> = _state.asStateFlow()

    private val _dishesState = MutableStateFlow<List<DishItem>>(emptyList())
    private val _categoriesState = MutableStateFlow<List<Category>>(emptyList())

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            _state.value = State.Loading

            try {
                val categoriesDeferred = async { loadCategories() }
                val dishesDeferred = async { loadDishes() }

                categoriesDeferred.await()
                dishesDeferred.await()

                updateCombinedState()
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }

    private suspend fun loadCategories() {
        getCategoriesUseCase.getCategoriesFlow()
            .catch { e ->
                _state.value = State.Error(e.message ?: "Category load error")
            }
            .collect { categories ->
                _categoriesState.value = categories.filterNotNull()
                updateCombinedState()
            }
    }

    private suspend fun loadDishes() {
        getMealsUseCase.getDishes()
            .catch { e ->
                _state.value = State.Error(e.message ?: "Dishes load error")
            }
            .collect { dish ->
                _dishesState.update { current ->
                    (current + dish).distinctBy { it.id }
                }
                updateCombinedState()
            }
    }

    fun getDishesByCategory(category: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                // Очищаем предыдущие блюда
                _dishesState.value = emptyList()

                getMealsUseCase.getDishesByCategory(category)?.let { flow ->
                    flow.catch { e ->
                        _state.value = State.Error(e.message ?: "Category load error")
                    }.collect { dish ->
                        _dishesState.update { current ->
                            (current + dish).distinctBy { it.id }
                        }
                        updateCombinedState()
                    }
                }
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun updateCombinedState() {
        val dishes = _dishesState.value
        val categories = _categoriesState.value

        when {
            dishes.isEmpty() && categories.isEmpty() ->
                _state.value = State.Loading

            dishes.isNotEmpty() && categories.isNotEmpty() ->
                _state.value = State.Success(Pair(dishes, categories))

            else -> {
                // Частичная загрузка
                _state.value = State.Success(Pair(dishes, categories))
            }
        }
    }
}
