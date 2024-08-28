package com.tz.fooddelivery.data.local.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaz4.data.local.dao.MealsDao
import com.kaz4.data.local.entities.CategoryEntity
import com.kaz4.data.local.entities.MealEntity
import com.tz.fooddelivery.data.local.dao.CategoriesDao

@Database(entities = [MealEntity::class, CategoryEntity::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealsDao(): MealsDao
    abstract fun categoriesDao(): CategoriesDao
}

