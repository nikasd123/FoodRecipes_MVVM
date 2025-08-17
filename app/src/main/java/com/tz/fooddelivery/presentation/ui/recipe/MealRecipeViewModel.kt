package com.tz.fooddelivery.presentation.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.use_cases.GetMealRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealRecipeViewModel @Inject constructor(
    private val getMealRecipeUseCase: GetMealRecipeUseCase
): ViewModel(){

    private val _recipe = MutableStateFlow<RecipeState>(RecipeState.Loading)
    val recipe: StateFlow<RecipeState> = _recipe.asStateFlow()

    private val _mealId = MutableLiveData<String>()
    val mealId: LiveData<String> = _mealId

    fun getMealRecipeById(id: String){
        viewModelScope.launch {
            when (val result = getMealRecipeUseCase.getMealRecipe(id)){
                is Result.Error -> _recipe.value = RecipeState.Error(
                    error = result.error,
                    message = "Recipe loading failed",
                    id = id
                )
                is Result.Success -> {
                    _recipe.value = RecipeState.Success(result.data)
                }
            }
        }
    }

    fun saveMealId(id: String){ _mealId.value = id }

    fun retry() {
        when (val current = _recipe.value) {
            is RecipeState.Error -> getMealRecipeById(current.id)
            else -> Unit
        }
    }
}