package com.tz.fooddelivery.di.module

import com.tz.fooddelivery.data.local.CategoriesDao
import com.tz.fooddelivery.data.local.DishesDao
import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.data.repository.DishesRepositoryImpl
import com.tz.fooddelivery.domain.repository.DishesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideDishesRepository(mealsApi: MealsApi, dishesDao: DishesDao, categoriesDao: CategoriesDao): DishesRepository =
        DishesRepositoryImpl(mealsApi = mealsApi, dishesDao = dishesDao, categoriesDao = categoriesDao)
}