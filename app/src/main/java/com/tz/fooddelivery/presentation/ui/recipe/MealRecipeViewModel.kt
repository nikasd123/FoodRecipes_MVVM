package com.tz.fooddelivery.presentation.ui.recipe

import androidx.lifecycle.ViewModel
import com.tz.fooddelivery.domain.use_cases.GetMealRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MealRecipeViewModel @Inject constructor(
    private val getMealRecipeUseCase: GetMealRecipeUseCase
): ViewModel(){

    fun getMealRecipeById(id: String){

    }
}