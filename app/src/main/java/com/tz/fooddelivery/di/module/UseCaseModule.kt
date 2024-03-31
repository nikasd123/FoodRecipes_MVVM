package com.tz.fooddelivery.di.module

import com.tz.fooddelivery.domain.repository.DishesRepository
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetDishesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetDishesUseCase(dishesRepository: DishesRepository) =
        GetDishesUseCase(dishesRepository = dishesRepository)

    @Provides
    fun provideGetCategoriesUseCase(dishesRepository: DishesRepository) =
        GetCategoriesUseCase(dishesRepository = dishesRepository)
}