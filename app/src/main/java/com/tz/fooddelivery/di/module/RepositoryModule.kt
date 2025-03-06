package com.tz.fooddelivery.di.module

import com.tz.fooddelivery.data.local.dao.CategoriesDao
import com.tz.fooddelivery.data.local.dao.DishesDao
import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.data.remote.api.TranslationApi
import com.tz.fooddelivery.data.repository.CategoryRepositoryImpl
import com.tz.fooddelivery.data.repository.MealsRepositoryImpl
import com.tz.fooddelivery.data.repository.TranslationRepositoryImpl
import com.tz.fooddelivery.domain.repository.CategoryRepository
import com.tz.fooddelivery.domain.repository.MealsRepository
import com.tz.fooddelivery.domain.repository.TranslationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideDishesRepository(mealsApi: MealsApi, dishesDao: DishesDao): MealsRepository =
        MealsRepositoryImpl(mealsApi = mealsApi, dishesDao = dishesDao)

    @Provides
    fun provideCategoryRepository(mealsApi: MealsApi, categoriesDao: CategoriesDao): CategoryRepository =
        CategoryRepositoryImpl(mealsApi = mealsApi, categoriesDao = categoriesDao)

    @Provides
    fun provideTranslationRepository(translationApi: TranslationApi): TranslationRepository =
        TranslationRepositoryImpl(translationApi = translationApi)
}