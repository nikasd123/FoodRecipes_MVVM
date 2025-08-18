package com.tz.fooddelivery.di.module

import com.tz.fooddelivery.data.local.data_source.LocalDataSource
import com.tz.fooddelivery.data.remote.api.TranslationApi
import com.tz.fooddelivery.data.repository.CategoryRepositoryImpl
import com.tz.fooddelivery.data.repository.MealRecipeRepositoryImpl
import com.tz.fooddelivery.data.repository.MealsRepositoryImpl
import com.tz.fooddelivery.data.repository.TranslationRepositoryImpl
import com.tz.fooddelivery.domain.repository.CategoryRepository
import com.tz.fooddelivery.domain.repository.MealRecipeRepository
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
    fun provideDishesRepository(localDataSource: LocalDataSource): MealsRepository =
        MealsRepositoryImpl(localDataSource = localDataSource,)

    @Provides
    fun provideCategoryRepository(localDataSource: LocalDataSource): CategoryRepository =
        CategoryRepositoryImpl(localDataSource = localDataSource)

    @Provides
    fun provideMealRecipeRepository(localDataSource: LocalDataSource): MealRecipeRepository =
        MealRecipeRepositoryImpl(localDataSource = localDataSource)

    @Provides
    fun provideTranslationRepository(translationApi: TranslationApi): TranslationRepository =
        TranslationRepositoryImpl(translationApi = translationApi)
}