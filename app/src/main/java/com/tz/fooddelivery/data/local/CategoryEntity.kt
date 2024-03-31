package com.tz.fooddelivery.data.local

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.tz.fooddelivery.domain.models.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val categoryId: String,
    val categoryName: String?
) {
    @Ignore
    constructor(): this("", null)
}

internal fun CategoryEntity.toDomain(): Category =
    Category(
        id = categoryId,
        category = categoryName ?: "",
        isActive = false
    )