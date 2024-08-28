package com.kaz4.foodrecipescompose.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaz4.domain.models.MealRecipe
import com.kaz4.domain.use_cases.GetMealRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealInfoViewModel @Inject constructor(
    private val getMealRecipeUseCase: GetMealRecipeUseCase
): ViewModel() {

    private val _mealRecipe = MutableLiveData<MealRecipe?>()
    val mealRecipe: LiveData<MealRecipe?> = _mealRecipe

    fun getMealRecipe(mealId: String){
        viewModelScope.launch {
            val response = getMealRecipeUseCase.getMealRecipe(mealId)
            _mealRecipe.value = response
        }
    }
}