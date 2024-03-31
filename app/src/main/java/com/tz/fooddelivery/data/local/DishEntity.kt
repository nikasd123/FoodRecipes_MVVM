package com.tz.fooddelivery.data.local

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.tz.fooddelivery.domain.models.DishItem

@Entity(tableName = "dishes")
data class DishEntity(
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

internal fun DishEntity.toDomain(): DishItem =
    DishItem(
        id = dishId,
        image = dishImage ?: "",
        title = dishTitle ?: "",
        category = dishCategory ?: "",
        description = description ?: ""
    )