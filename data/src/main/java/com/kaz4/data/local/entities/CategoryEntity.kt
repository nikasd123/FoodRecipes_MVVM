package com.kaz4.data.local.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.kaz4.domain.models.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val categoryId: String?,
    val categoryName: String?
){
    @Ignore constructor(): this(0, null, null)
}

internal fun CategoryEntity.toDomain(): Category =
    Category(
        id = categoryId ?: "0",
        category = categoryName ?: "",
        isActive = false
    )

internal fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        categoryId = id,
        categoryName = category
    )