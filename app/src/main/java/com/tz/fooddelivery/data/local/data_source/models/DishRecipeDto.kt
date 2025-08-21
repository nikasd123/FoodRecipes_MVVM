package com.tz.fooddelivery.data.local.data_source.models

import com.google.gson.annotations.SerializedName
import com.tz.fooddelivery.domain.models.IngredientItem
import com.tz.fooddelivery.domain.models.MealRecipe

data class DishRecipeDto(
    @SerializedName("idMeal") val idMeal: String?,
    @SerializedName("strMeal") val title: String?,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strArea") val area: String?,
    @SerializedName("strInstructions") val instructions: String?,
    @SerializedName("strMealThumb") val image: String?,
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
    @SerializedName("ruIngredient1") val ruIngredient1: String?,
    @SerializedName("ruIngredient2") val ruIngredient2: String?,
    @SerializedName("ruIngredient3") val ruIngredient3: String?,
    @SerializedName("ruIngredient4") val ruIngredient4: String?,
    @SerializedName("ruIngredient5") val ruIngredient5: String?,
    @SerializedName("ruIngredient6") val ruIngredient6: String?,
    @SerializedName("ruIngredient7") val ruIngredient7: String?,
    @SerializedName("ruIngredient8") val ruIngredient8: String?,
    @SerializedName("ruIngredient9") val ruIngredient9: String?,
    @SerializedName("ruIngredient10") val ruIngredient10: String?,
    @SerializedName("ruIngredient11") val ruIngredient11: String?,
    @SerializedName("ruIngredient12") val ruIngredient12: String?,
    @SerializedName("ruIngredient13") val ruIngredient13: String?,
    @SerializedName("ruIngredient14") val ruIngredient14: String?,
    @SerializedName("ruIngredient15") val ruIngredient15: String?,
    @SerializedName("ruIngredient16") val ruIngredient16: String?,
    @SerializedName("ruIngredient17") val ruIngredient17: String?,
    @SerializedName("ruIngredient18") val ruIngredient18: String?,
    @SerializedName("ruIngredient19") val ruIngredient19: String?,
    @SerializedName("ruIngredient20") val ruIngredient20: String?,
    @SerializedName("ruIngredient21") val ruIngredient21: String?,
    @SerializedName("ruIngredient22") val ruIngredient22: String?,
    @SerializedName("ruIngredient23") val ruIngredient23: String?,
    @SerializedName("ruIngredient24") val ruIngredient24: String?,
    @SerializedName("ruIngredient25") val ruIngredient25: String?,
    @SerializedName("ruIngredient26") val ruIngredient26: String?,
    @SerializedName("ruIngredient27") val ruIngredient27: String?,
    @SerializedName("ruIngredient28") val ruIngredient28: String?,
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
) {
    fun toDomain(): MealRecipe {
        val ingredients = mutableListOf<IngredientItem>()
        for (i in 1..20) {
            val ingredientField = this::class.java.getDeclaredField("strIngredient$i")
            val ruIngredientField = this::class.java.getDeclaredField("ruIngredient$i")
            val measureField = this::class.java.getDeclaredField("strMeasure$i")

            ruIngredientField.isAccessible = true
            ingredientField.isAccessible = true
            measureField.isAccessible = true

            val ruIngredient = ruIngredientField.get(this) as? String
            val ingredient = ingredientField.get(this) as? String
            val measure = measureField.get(this) as? String

            if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank() && !ruIngredient.isNullOrBlank()) {
                ingredients.add(
                    IngredientItem(
                        translatedName = ruIngredient,
                        translatedMeasure = measure,
                        imageUrl = buildImageUrl(ingredient)
                    )
                )
            }
        }

        return MealRecipe(
            idMeal = idMeal ?: "",
            mealTitle = title ?: "",
            mealCategory = category ?: "",
            area = area ?: "",
            mealRecipe = instructions ?: "",
            mealImage = image ?: "",
            ingredientsList = ingredients,
            strYoutube = strYoutube ?: "",
            ingredients = ""
        )
    }

    private fun buildImageUrl(ingredientName: String): String {
        val formattedName = ingredientName
            .trim()
            .replace(" ", "_")
            .lowercase()
        return "https://www.themealdb.com/images/ingredients/$formattedName.png"
    }
}