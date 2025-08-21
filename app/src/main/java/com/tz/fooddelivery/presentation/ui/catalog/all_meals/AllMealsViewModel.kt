package com.tz.fooddelivery.presentation.ui.catalog.all_meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.use_cases.GetAllDishesUseCase
import com.tz.fooddelivery.domain.use_cases.GetFavoriteDishesUseCase
import com.tz.fooddelivery.domain.use_cases.SetFavoriteDishUseCase
import com.tz.fooddelivery.presentation.common.mapError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMealsViewModel @Inject constructor(
    private val setFavoriteDishUseCase: SetFavoriteDishUseCase,
    private val getFavoriteDishesUseCase: GetFavoriteDishesUseCase,
    private val getAllDishesUseCase: GetAllDishesUseCase
) : ViewModel() {

    private val _allDishesState = MutableStateFlow<AllDishesState>(AllDishesState.Loading)
    val allDishesState = _allDishesState.asStateFlow()


    init{
        loadInitialData()
    }


    private fun loadInitialData(){
        loadDishes()
    }

    private fun loadDishes(){
        viewModelScope.launch {
            _allDishesState.value = AllDishesState.Loading
            getAllDishesUseCase.getAllDishes()
                .catch { e ->
                    _allDishesState.value = AllDishesState.Error(
                        error = mapError(e),
                        message = "Failed to load All Dishes"
                    )
                }
                .collect{ result ->
                    when(result){
                        is Result.Success -> {
                            _allDishesState.value = AllDishesState.Success(result.data)
                        }
                        is Result.Error -> {
                            _allDishesState.value = AllDishesState.Error(
                                error = result.error,
                                message = "Failed to load all dishes"
                            )
                        }
                    }
                }
        }
    }

    fun handleFavoriteButtonClick(dishItem: DishItem) {
        viewModelScope.launch {
            val success = setFavoriteDishUseCase.setFavoriteDish(dishId = dishItem.id)
        }

    }
}