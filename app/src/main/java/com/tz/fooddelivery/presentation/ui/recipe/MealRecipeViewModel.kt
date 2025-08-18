package com.tz.fooddelivery.presentation.ui.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.use_cases.GetMealRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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
        Log.d("MealRecipeVM", "Запрос рецепта по id: $id")
        viewModelScope.launch {
            try {
                when (val result = getMealRecipeUseCase.getMealRecipe(id)) {
                    is Result.Error -> {
                        Log.e("MealRecipeVM", "Ошибка загрузки рецепта: ${result.error}")
                        _recipe.value = RecipeState.Error(
                            error = result.error,
                            message = "Recipe loading failed",
                            id = id
                        )
                    }
                    is Result.Success -> {
                        Log.d("MealRecipeVM", "Рецепт успешно загружен: ${result.data}")
                        _recipe.value = RecipeState.Success(result.data)
                    }
                }
            } catch (e: Exception) {
                Log.e("MealRecipeVM", "Непредвиденная ошибка при загрузке рецепта", e)
                _recipe.value = RecipeState.Error(
                    error = NetworkError.UNKNOWN_ERROR,
                    message = "Unexpected error",
                    id = id
                )
            }
        }
    }


    sealed class Event {
        data class OpenYoutube(val url: String) : Event()
    }

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    fun openYoutube(url: String) {
        if (url.isBlank()) return
        viewModelScope.launch {
            _event.send(Event.OpenYoutube(url))
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