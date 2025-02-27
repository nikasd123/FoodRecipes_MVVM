package com.tz.fooddelivery.domain.common.mappers

import com.tz.fooddelivery.data.local.entities.CategoryEntity
import com.tz.fooddelivery.data.local.entities.DishEntity
import com.tz.fooddelivery.data.local.entities.toDomain
import com.tz.fooddelivery.data.remote.dto.CategoryDto
import com.tz.fooddelivery.data.remote.dto.DishItemDto
import com.tz.fooddelivery.data.remote.dto.toDomain
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.models.toEntity

// Extension functions for conversions
internal fun List<DishItemDto>.toDishItems() = map { it.toDomain() }
internal fun List<CategoryDto>.toCategories() = map { it.toDomain() }
internal fun List<CategoryEntity>.toCategoriesFromEntity() = map { it.toDomain() }
internal fun List<DishEntity>.toDishItemsFromEntity() = map { it.toDomain() }
internal fun List<DishItem>.toDishEntities() = map { it.toEntity() }
internal fun List<Category>.toCategoryEntities() = map { it.toEntity() }