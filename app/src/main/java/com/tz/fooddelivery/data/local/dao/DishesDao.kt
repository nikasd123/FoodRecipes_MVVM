package com.tz.fooddelivery.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tz.fooddelivery.data.local.entities.DishEntity

@Dao
abstract class DishesDao {
    @Query("SELECT * FROM dishes")
    abstract fun getAllDishes(): List<DishEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(dishes: List<DishEntity>)

    @Query("SELECT * FROM dishes WHERE dishCategory = :category")
    abstract fun getDishesByCategory(category: String): List<DishEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setDishesByCategory(dishes: List<DishEntity>)

    @Query("DELETE FROM dishes WHERE dishCategory = :category")
    abstract fun clearByCategory(category: String)

    @Query("SELECT * FROM dishes WHERE isFavorite = 1" )
    abstract fun getFavoriteDishes() : List<DishEntity>

    @Query("UPDATE dishes SET isFavorite = 1 - isFavorite WHERE dishId = :dishId")
    abstract fun toggleFavoriteDish(dishId: String) : Int
}