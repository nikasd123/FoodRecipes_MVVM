package com.tz.fooddelivery.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tz.fooddelivery.data.local.entities.MealEntity

@Dao
abstract class MealsDao {
    @Query("SELECT * FROM dishes")
    abstract fun getAllDishes(): List<MealEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(dishes: List<MealEntity>)

    @Query("SELECT * FROM dishes WHERE dishCategory = :category")
    abstract fun getDishesByCategory(category: String): List<MealEntity>
}