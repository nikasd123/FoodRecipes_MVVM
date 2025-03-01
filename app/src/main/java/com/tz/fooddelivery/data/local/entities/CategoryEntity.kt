package com.tz.fooddelivery.data.local.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.tz.fooddelivery.domain.models.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val categoryId: String,
    val originalName: String?,
    val categoryName: String?
) {
    @Ignore
    constructor(): this("", null, null)
}

internal fun CategoryEntity.toDomain(): Category =
    Category(
        id = categoryId,
        category = categoryName ?: "",
        originalName = originalName ?: "",
        isActive = false
    )