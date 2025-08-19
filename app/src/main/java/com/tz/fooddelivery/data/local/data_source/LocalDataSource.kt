package com.tz.fooddelivery.data.local.data_source

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.tz.fooddelivery.data.local.data_source.models.CategoriesResponse
import com.tz.fooddelivery.data.local.data_source.models.MealsRecipeResponse
import com.tz.fooddelivery.data.local.data_source.models.MealsResponse
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.models.MealRecipe
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class LocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val gson = Gson()

    private var cachedCategories: List<Category>? = null
    private var cachedRecipes: List<MealRecipe>? = null

    suspend fun getCategories(): List<Category> {
        return cachedCategories ?: run {
            try {
                val json = context.assets.open("categories.json").bufferedReader().use { it.readText() }
                val response = gson.fromJson(json, CategoriesResponse::class.java)
                response.categories
                    .map { it.toDomain() }
                    .also {
                        cachedCategories = it
                        Log.d("LocalDataSource", "Loaded ${it.size} categories")
                    }
            } catch (e: Exception) {
                Log.e("LocalDataSource", "Error loading categories", e)
                emptyList()
            }
        }
    }

    suspend fun getDishesByCategory(category: String): List<DishItem> {
        val fileName = "${category}.json"
        val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val response = gson.fromJson(json, MealsResponse::class.java)
        return response.meals.map { it.toDomain(category) }
    }

    suspend fun getDishById(id: String): MealRecipe? {
        return try {
            val json = withContext(Dispatchers.IO) {
                context.assets.open("recipes.json").bufferedReader().use { it.readText() }
            }
            val response = gson.fromJson(json, MealsRecipeResponse::class.java)
            response.meals.find { it.idMeal == id }?.toDomain()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("LocalDataSource", "Error loading recipe $id", e)
            null
        }
    }
}