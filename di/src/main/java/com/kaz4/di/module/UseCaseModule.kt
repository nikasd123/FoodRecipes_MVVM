package com.kaz4.di.module

import com.kaz4.domain.repository.MealsRepository
import com.kaz4.domain.repository.RecipeRepository
import com.kaz4.domain.repository.TranslationRepository
import com.kaz4.domain.use_cases.GetCategoriesUseCase
import com.kaz4.domain.use_cases.GetMealRecipeUseCase
import com.kaz4.domain.use_cases.GetMealsUseCase
import com.kaz4.domain.use_cases.GetTranslatedTextUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun getMealsUseCase(mealsRepository: MealsRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetMealsUseCase(mealsRepository = mealsRepository, getTranslatedTextUseCase = getTranslatedTextUseCase)

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(mealsRepository: MealsRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetCategoriesUseCase(mealsRepository = mealsRepository, getTranslatedTextUseCase = getTranslatedTextUseCase)

    @Provides
    @Singleton
    fun provideGetMealRecipeUseCase(recipeRepository: RecipeRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetMealRecipeUseCase(recipeRepository = recipeRepository, getTranslatedTextUseCase = getTranslatedTextUseCase)

    @Provides
    @Singleton
    fun provideGetTranslatedTextUseCase(translationRepository: TranslationRepository) =
        GetTranslatedTextUseCase(translationRepository = translationRepository)
}