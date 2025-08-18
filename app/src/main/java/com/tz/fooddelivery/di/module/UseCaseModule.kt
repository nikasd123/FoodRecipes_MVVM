package com.tz.fooddelivery.di.module

import android.content.Context
import com.tz.fooddelivery.data.local.data_source.LocalDataSource
import com.tz.fooddelivery.domain.repository.CategoryRepository
import com.tz.fooddelivery.domain.repository.MealRecipeRepository
import com.tz.fooddelivery.domain.repository.MealsRepository
import com.tz.fooddelivery.domain.repository.TranslationRepository
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealRecipeUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealsUseCase
import com.tz.fooddelivery.domain.use_cases.GetTranslatedTextUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun getMealsUseCase(mealsRepository: MealsRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetMealsUseCase(mealsRepository = mealsRepository)

    @Provides
    fun provideGetCategoriesUseCase(categoryRepository: CategoryRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetCategoriesUseCase(categoryRepository = categoryRepository)

    @Provides
    fun provideGetMealRecipeUseCase(mealRecipeRepository: MealRecipeRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetMealRecipeUseCase(mealRecipeRepository = mealRecipeRepository)

    @Provides
    fun provideGetTranslatedTextUseCase(translationRepository: TranslationRepository) =
        GetTranslatedTextUseCase(translationRepository = translationRepository)

    @Provides
    @Singleton
    fun provideMealsLocalDataSource(@ApplicationContext context: Context): LocalDataSource {
        return LocalDataSource(context)
    }
}