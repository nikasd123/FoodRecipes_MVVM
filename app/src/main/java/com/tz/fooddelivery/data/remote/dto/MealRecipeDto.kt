package com.tz.fooddelivery.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.tz.fooddelivery.domain.models.IngredientItem
import com.tz.fooddelivery.domain.models.MealRecipe

data class MealRecipeDto(
    @SerializedName("idMeal") val idMeal: String?,
    @SerializedName("strMeal") val mealTitle: String?,
    @SerializedName("strDrinkAlternate") val strDrinkAlternate: String?,
    @SerializedName("strCategory") val mealCategory: String?,
    @SerializedName("strArea") val strArea: String?,
    @SerializedName("strInstructions") val mealRecipe: String?,
    @SerializedName("strMealThumb") val mealImage: String?,
    @SerializedName("strYoutube") val strYoutube: String?,
    @SerializedName("strIngredient1") val strIngredient1: String?,
    @SerializedName("strIngredient2") val strIngredient2: String?,
    @SerializedName("strIngredient3") val strIngredient3: String?,
    @SerializedName("strIngredient4") val strIngredient4: String?,
    @SerializedName("strIngredient5") val strIngredient5: String?,
    @SerializedName("strIngredient6") val strIngredient6: String?,
    @SerializedName("strIngredient7") val strIngredient7: String?,
    @SerializedName("strIngredient8") val strIngredient8: String?,
    @SerializedName("strIngredient9") val strIngredient9: String?,
    @SerializedName("strIngredient10") val strIngredient10: String?,
    @SerializedName("strIngredient11") val strIngredient11: String?,
    @SerializedName("strIngredient12") val strIngredient12: String?,
    @SerializedName("strIngredient13") val strIngredient13: String?,
    @SerializedName("strIngredient14") val strIngredient14: String?,
    @SerializedName("strIngredient15") val strIngredient15: String?,
    @SerializedName("strIngredient16") val strIngredient16: String?,
    @SerializedName("strIngredient17") val strIngredient17: String?,
    @SerializedName("strIngredient18") val strIngredient18: String?,
    @SerializedName("strIngredient19") val strIngredient19: String?,
    @SerializedName("strIngredient20") val strIngredient20: String?,
    @SerializedName("strIngredient21") val strIngredient21: String?,
    @SerializedName("strIngredient22") val strIngredient22: String?,
    @SerializedName("strIngredient23") val strIngredient23: String?,
    @SerializedName("strIngredient24") val strIngredient24: String?,
    @SerializedName("strIngredient25") val strIngredient25: String?,
    @SerializedName("strIngredient26") val strIngredient26: String?,
    @SerializedName("strIngredient27") val strIngredient27: String?,
    @SerializedName("strIngredient28") val strIngredient28: String?,
    @SerializedName("strMeasure1") val strMeasure1: String?,
    @SerializedName("strMeasure2") val strMeasure2: String?,
    @SerializedName("strMeasure3") val strMeasure3: String?,
    @SerializedName("strMeasure4") val strMeasure4: String?,
    @SerializedName("strMeasure5") val strMeasure5: String?,
    @SerializedName("strMeasure6") val strMeasure6: String?,
    @SerializedName("strMeasure7") val strMeasure7: String?,
    @SerializedName("strMeasure8") val strMeasure8: String?,
    @SerializedName("strMeasure9") val strMeasure9: String?,
    @SerializedName("strMeasure10") val strMeasure10: String?,
    @SerializedName("strMeasure11") val strMeasure11: String?,
    @SerializedName("strMeasure12") val strMeasure12: String?,
    @SerializedName("strMeasure13") val strMeasure13: String?,
    @SerializedName("strMeasure14") val strMeasure14: String?,
    @SerializedName("strMeasure15") val strMeasure15: String?,
    @SerializedName("strMeasure16") val strMeasure16: String?,
    @SerializedName("strMeasure17") val strMeasure17: String?,
    @SerializedName("strMeasure18") val strMeasure18: String?,
    @SerializedName("strMeasure19") val strMeasure19: String?,
    @SerializedName("strMeasure20") val strMeasure20: String?,
    @SerializedName("strMeasure21") val strMeasure21: String?,
    @SerializedName("strMeasure22") val strMeasure22: String?,
    @SerializedName("strMeasure23") val strMeasure23: String?,
    @SerializedName("strMeasure24") val strMeasure24: String?,
    @SerializedName("strMeasure25") val strMeasure25: String?,
    @SerializedName("strMeasure26") val strMeasure26: String?,
    @SerializedName("strMeasure27") val strMeasure27: String?,
    @SerializedName("strMeasure28") val strMeasure28: String?,
)

fun MealRecipeDto.toDomain(): MealRecipe {
    // Создаем списки ингредиентов и мер
    val ingredients = listOf(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
        strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20,
        strIngredient21, strIngredient22, strIngredient23, strIngredient24, strIngredient25,
        strIngredient26, strIngredient27, strIngredient28
    )

    val measures = listOf(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
        strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20,
        strMeasure21, strMeasure22, strMeasure23, strMeasure24, strMeasure25,
        strMeasure26, strMeasure27, strMeasure28
    )

    // Формируем список IngredientItem
    val ingredientItems = ingredients.zip(measures) { name, measure ->
        name?.takeIf { it.isNotEmpty() }?.let {
            IngredientItem(
                translatedName = it,
                originalName = it,
                translatedMeasure = measure ?: "",
                originalMeasure = measure ?: "",
                imageUrl = buildImageUrl(it)
            )
        }
    }.filterNotNull()

    // Формируем строку ингредиентов для обратной совместимости
    val ingredientsString = ingredientItems.joinToString(", ") {
        "${it.translatedName}${if (it.translatedMeasure.isNotEmpty()) ": ${it.translatedMeasure}" else ""}"
    }

    return MealRecipe(
        idMeal = idMeal ?: "",
        mealTitle = mealTitle ?: "",
        mealCategory = mealCategory ?: "",
        area = strArea ?: "",
        mealRecipe = mealRecipe ?: "",
        mealImage = mealImage ?: "",
        strYoutube = strYoutube ?: "",
        ingredients = ingredientsString,
        ingredientsList = ingredientItems
    )
}

private fun buildImageUrl(ingredientName: String): String {
    val formattedName = ingredientName
        .trim()
        .replace(" ", "_")
        .lowercase()
    return "https://www.themealdb.com/images/ingredients/$formattedName-medium.png"
}