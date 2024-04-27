package com.tz.fooddelivery.di.module

import com.tz.fooddelivery.domain.repository.MealsRepository
import com.tz.fooddelivery.domain.repository.RecipeRepository
import com.tz.fooddelivery.domain.repository.TranslationRepository
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetMealRecipeUseCase
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
    fun provideGetCategoriesUseCase(mealsRepository: MealsRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetCategoriesUseCase(mealsRepository = mealsRepository, getTranslatedTextUseCase = getTranslatedTextUseCase)

    @Provides
    fun provideGetMealRecipeUseCase(recipeRepository: RecipeRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetMealRecipeUseCase(recipeRepository = recipeRepository, getTranslatedTextUseCase = getTranslatedTextUseCase)

    @Provides
    fun provideGetTranslatedTextUseCase(translationRepository: TranslationRepository) =
        GetTranslatedTextUseCase(translationRepository = translationRepository)
}