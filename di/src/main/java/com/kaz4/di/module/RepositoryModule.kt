package com.kaz4.di.module

import com.kaz4.data.local.dao.MealsDao
import com.kaz4.data.remote.api.MealsApi
import com.kaz4.data.remote.api.TranslationApi
import com.kaz4.data.repository.MealsRepositoryImpl
import com.kaz4.data.repository.RecipeRepositoryImpl
import com.kaz4.data.repository.TranslationRepositoryImpl
import com.kaz4.domain.repository.MealsRepository
import com.kaz4.domain.repository.RecipeRepository
import com.kaz4.domain.repository.TranslationRepository
import com.tz.fooddelivery.data.local.dao.CategoriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDishesRepository(mealsApi: MealsApi, mealsDao: MealsDao, categoriesDao: CategoriesDao): MealsRepository =
        MealsRepositoryImpl(mealsApi = mealsApi, mealsDao = mealsDao, categoriesDao = categoriesDao)

    @Provides
    @Singleton
    fun provideTranslationRepository(translationApi: TranslationApi): TranslationRepository =
        TranslationRepositoryImpl(translationApi = translationApi)

    @Provides
    @Singleton
    fun provideRecipeRepository(mealsApi: MealsApi): RecipeRepository =
        RecipeRepositoryImpl(mealsApi = mealsApi)
}