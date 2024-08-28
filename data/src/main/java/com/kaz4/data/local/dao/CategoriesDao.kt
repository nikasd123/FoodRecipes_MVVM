package com.tz.fooddelivery.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaz4.data.local.entities.CategoryEntity

@Dao
abstract class CategoriesDao {
    @Query("SELECT * FROM categories")
    abstract fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(categories: List<CategoryEntity>)
}
