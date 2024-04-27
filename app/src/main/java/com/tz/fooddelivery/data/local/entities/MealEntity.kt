package com.tz.fooddelivery.data.local.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.tz.fooddelivery.domain.models.MealItem

@Entity(tableName = "dishes")
data class MealEntity(
    @PrimaryKey
    val dishId: String,
    val dishImage: String?,
    val dishTitle: String?,
    val dishCategory: String?,
    val description: String?
) {
    @Ignore
    constructor(): this("", null, null, null, null)
}

internal fun MealEntity.toDomain(): MealItem =
    MealItem(
        id = dishId,
        image = dishImage ?: "",
        title = dishTitle ?: "",
        category = dishCategory ?: "",
        description = description ?: ""
    )