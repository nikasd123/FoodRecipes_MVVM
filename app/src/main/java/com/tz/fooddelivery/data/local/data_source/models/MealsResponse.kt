package com.tz.fooddelivery.data.local.data_source.models

import com.google.gson.annotations.SerializedName

data class MealsResponse(
    @SerializedName("meals") val meals: List<DishItemDto>
)