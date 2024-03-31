package com.tz.fooddelivery.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class CategoriesDao {
    @Query("SELECT * FROM categories")
    abstract fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(categories: List<CategoryEntity>)
}
