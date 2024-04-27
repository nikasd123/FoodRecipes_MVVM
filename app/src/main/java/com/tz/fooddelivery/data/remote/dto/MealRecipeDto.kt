package com.tz.fooddelivery.data.remote.dto

import com.google.gson.annotations.SerializedName
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
    @SerializedName("strIngredient20") val strIngredient20: String?
)

fun MealRecipeDto.toDomain(): MealRecipe =
    MealRecipe(
        idMeal = idMeal ?: "",
        mealTitle = mealTitle ?: "",
        mealCategory = mealCategory ?: "",
        area = strArea ?: "",
        mealRecipe = mealRecipe ?: "",
        mealImage = mealImage ?: "",
        strYoutube = strYoutube ?: "",
        ingredients = listOfNotNull(
            strIngredient1.takeIf { it?.isNotEmpty() == true },
            strIngredient2.takeIf { it?.isNotEmpty() == true },
            strIngredient3.takeIf { it?.isNotEmpty() == true },
            strIngredient4.takeIf { it?.isNotEmpty() == true },
            strIngredient5.takeIf { it?.isNotEmpty() == true },
            strIngredient6.takeIf { it?.isNotEmpty() == true },
            strIngredient7.takeIf { it?.isNotEmpty() == true },
            strIngredient8.takeIf { it?.isNotEmpty() == true },
            strIngredient9.takeIf { it?.isNotEmpty() == true },
            strIngredient10.takeIf { it?.isNotEmpty() == true },
            strIngredient11.takeIf { it?.isNotEmpty() == true },
            strIngredient12.takeIf { it?.isNotEmpty() == true },
            strIngredient13.takeIf { it?.isNotEmpty() == true },
            strIngredient14.takeIf { it?.isNotEmpty() == true },
            strIngredient15.takeIf { it?.isNotEmpty() == true },
            strIngredient16.takeIf { it?.isNotEmpty() == true },
            strIngredient17.takeIf { it?.isNotEmpty() == true },
            strIngredient18.takeIf { it?.isNotEmpty() == true },
            strIngredient19.takeIf { it?.isNotEmpty() == true },
            strIngredient20.takeIf { it?.isNotEmpty() == true },
        ).joinToString(", ")
    )
