package com.tz.fooddelivery.di.module

import com.tz.fooddelivery.data.local.dao.CategoriesDao
import com.tz.fooddelivery.data.local.dao.DishesDao
import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.data.repository.MealsRepositoryImpl
import com.tz.fooddelivery.domain.repository.MealsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideDishesRepository(mealsApi: MealsApi, dishesDao: DishesDao, categoriesDao: CategoriesDao): MealsRepository =
        MealsRepositoryImpl(mealsApi = mealsApi, dishesDao = dishesDao, categoriesDao = categoriesDao)
}