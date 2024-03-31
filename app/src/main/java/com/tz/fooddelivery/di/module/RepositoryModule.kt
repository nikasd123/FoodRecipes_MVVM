package com.tz.fooddelivery.di.module

import com.tz.fooddelivery.data.api.MealsApi
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
    fun provideDishesRepository(mealsApi: MealsApi): DishesRepository =
        DishesRepositoryImpl(mealsApi = mealsApi)
}