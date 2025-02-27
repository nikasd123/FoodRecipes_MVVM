package com.tz.fooddelivery.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.common.State
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias PairMealsAndCategoryList = Pair<List<DishItem>, List<Category>>

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsUseCase: GetMealsUseCase
) : ViewModel() {

    private val _state = MutableSharedFlow<State<PairMealsAndCategoryList>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val state: SharedFlow<State<PairMealsAndCategoryList>> = _state.asSharedFlow()

    private val _dishesState = MutableStateFlow<List<DishItem>>(emptyList())
    private val _categoriesState = MutableStateFlow<List<Category>>(emptyList())

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            _state.emit(State.Loading)

            try {
                val categoriesDeferred = async { loadCategories() }
                val dishesDeferred = async { loadDishes() }

                categoriesDeferred.await()
                dishesDeferred.await()

                updateCombinedState()
            } catch (e: Exception) {
                _state.emit(State.Error(e.message ?: "Unknown error"))
            }
        }
    }

    private suspend fun loadCategories() {
        getCategoriesUseCase.getCategoriesFlow()
            .buffer(10)
            .scan(emptyList<Category?>()) { acc, value -> acc + value }
            .mapLatest { it.distinctBy { item -> item?.id } }
            .catch { e ->
                _state.emit(State.Error(e.message ?: "Category load error"))
            }
            .collect { categories ->
                _categoriesState.value = categories.filterNotNull()
                updateCombinedState()
            }
    }

    private suspend fun loadDishes() {
        getMealsUseCase.getDishes()
            .buffer(10)
            .scan(emptyList<DishItem>()) { acc, value -> acc + value }
            .mapLatest { it.distinctBy { item -> item.id } }
            .catch { e ->
                _state.emit(State.Error(e.message ?: "Dishes load error"))
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
            _state.emit(State.Loading)
            try {
                _dishesState.value = emptyList()

                getMealsUseCase.getDishesByCategory(category)?.let { flow ->
                    flow.catch { e ->
                        _state.emit(State.Error(e.message ?: "Category load error"))
                    }.collect { dish ->
                        _dishesState.update { current ->
                            (current + dish).distinctBy { it.id }
                        }
                        updateCombinedState()
                    }
                }
            } catch (e: Exception) {
                _state.emit(State.Error(e.message ?: "Unknown error"))
            }
        }
    }

    private suspend fun updateCombinedState() {
        val dishes = _dishesState.value
        val categories = _categoriesState.value

        when {
            dishes.isEmpty() && categories.isEmpty() ->
                _state.emit(State.Loading)

            dishes.isNotEmpty() && categories.isNotEmpty() ->
                _state.emit(State.Success(Pair(dishes, categories)))

            else -> {
                _state.emit(State.Success(Pair(dishes, categories)))
            }
        }
    }
}
