package com.tz.fooddelivery.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DishEntity::class, CategoryEntity::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishesDao(): DishesDao
    abstract fun categoriesDao(): CategoriesDao
}

