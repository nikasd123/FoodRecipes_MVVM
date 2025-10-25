package com.tz.fooddelivery.di.module

import com.tz.fooddelivery.domain.repository.CategoryRepository
import com.tz.fooddelivery.domain.repository.MealRecipeRepository
import com.tz.fooddelivery.domain.repository.MealsRepository
import com.tz.fooddelivery.domain.repository.TranslationRepository
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealRecipeUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealUpdatesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealsUseCase
import com.tz.fooddelivery.domain.use_cases.GetTranslatedTextUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun getMealsUseCase(mealsRepository: MealsRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetMealsUseCase(mealsRepository = mealsRepository, getTranslatedTextUseCase = getTranslatedTextUseCase)

    @Provides
    fun provideGetMealUpdatesUseCase(mealsRepository: MealsRepository) =
        GetMealUpdatesUseCase(mealsRepository = mealsRepository)

    @Provides
    fun provideGetCategoriesUseCase(categoryRepository: CategoryRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetCategoriesUseCase(categoryRepository = categoryRepository, translateUseCase = getTranslatedTextUseCase)

    @Provides
    fun provideGetMealRecipeUseCase(mealRecipeRepository: MealRecipeRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetMealRecipeUseCase(mealRecipeRepository = mealRecipeRepository, translateTextUseCase = getTranslatedTextUseCase)

    @Provides
    fun provideGetTranslatedTextUseCase(translationRepository: TranslationRepository) =
        GetTranslatedTextUseCase(translationRepository = translationRepository)
}