package com.tz.fooddelivery.data.repository

import android.util.Log
import com.tz.fooddelivery.data.local.data_source.LocalDataSource
import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.MealRecipe
import com.tz.fooddelivery.domain.repository.MealRecipeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealRecipeRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): MealRecipeRepository {

    override suspend fun getDishById(id: String): Result<MealRecipe, DataError> {
        try {
            Log.d("MealRecipeRepo", "Loading recipe for id=$id")
            val recipe = withContext(ioDispatcher) {
                localDataSource.getDishById(id).also {
                    Log.d("MealRecipeRepo", "Received recipe: ${it?.idMeal}")
                }
            }

            if (recipe != null) {
                Log.d("MealRecipeRepo", "Emitting success for $id")
                return Result.Success(recipe)
            } else {
                Log.w("MealRecipeRepo", "Recipe not found for $id")
                return Result.Error(DataError.Local.DATABASE_ERROR)
            }
        } catch (e: Exception) {
            Log.e("MealRecipeRepo", "Error loading recipe $id", e)
            return Result.Error(DataError.Local.DATABASE_ERROR)
        }
    }
}