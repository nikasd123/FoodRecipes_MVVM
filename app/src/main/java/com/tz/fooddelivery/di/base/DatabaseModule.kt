package com.tz.fooddelivery.di.base

import android.content.Context
import androidx.room.Room
import com.tz.fooddelivery.data.local.AppDatabase
import com.tz.fooddelivery.data.local.CategoryEntity
import com.tz.fooddelivery.data.local.DishEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context, AppDatabase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDishesDao(database: AppDatabase) = database.dishesDao()

    @Provides
    @Singleton
    fun provideDishEntity() = DishEntity()

    @Provides
    @Singleton
    fun provideCategoriesDao(database: AppDatabase) = database.categoriesDao()

    @Provides
    @Singleton
    fun provideCategoryEntity() = CategoryEntity()
}