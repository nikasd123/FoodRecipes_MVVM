package com.tz.fooddelivery.di.module

import com.tz.fooddelivery.data.local.dao.CategoriesDao
import com.tz.fooddelivery.data.local.dao.MealsDao
import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.data.remote.api.TranslationApi
import com.tz.fooddelivery.data.repository.MealsRepositoryImpl
import com.tz.fooddelivery.data.repository.TranslationRepositoryImpl
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
    fun provideDishesRepository(mealsApi: MealsApi, mealsDao: MealsDao, categoriesDao: CategoriesDao): MealsRepository =
        MealsRepositoryImpl(mealsApi = mealsApi, mealsDao = mealsDao, categoriesDao = categoriesDao)

    @Provides
    fun provideTranslationRepository(translationApi: TranslationApi): TranslationRepository =
        TranslationRepositoryImpl(translationApi = translationApi)
}