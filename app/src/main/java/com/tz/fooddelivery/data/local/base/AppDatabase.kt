package com.tz.fooddelivery.data.local.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tz.fooddelivery.data.local.dao.CategoriesDao
import com.tz.fooddelivery.data.local.entities.CategoryEntity
import com.tz.fooddelivery.data.local.entities.DishEntity
import com.tz.fooddelivery.data.local.dao.DishesDao

@Database(entities = [DishEntity::class, CategoryEntity::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishesDao(): DishesDao
    abstract fun categoriesDao(): CategoriesDao
}

