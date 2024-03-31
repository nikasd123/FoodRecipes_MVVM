package com.tz.fooddelivery.data.dto

import com.google.gson.annotations.SerializedName
import com.tz.fooddelivery.domain.models.DishItem

data class MealsResponse(
    @SerializedName("meals") val meals: List<DishItem>?
)
