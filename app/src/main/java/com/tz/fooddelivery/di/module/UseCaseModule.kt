package com.tz.fooddelivery.di.module

import com.tz.fooddelivery.domain.repository.MealsRepository
import com.tz.fooddelivery.domain.repository.TranslationRepository
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
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
    fun provideGetDishesUseCase(mealsRepository: MealsRepository, getTranslatedTextUseCase: GetTranslatedTextUseCase) =
        GetMealsUseCase(mealsRepository = mealsRepository, getTranslatedTextUseCase = getTranslatedTextUseCase)

    @Provides
    fun provideGetCategoriesUseCase(mealsRepository: MealsRepository) =
        GetCategoriesUseCase(mealsRepository = mealsRepository)

    @Provides
    fun provideGetTranslatedTextUseCase(translationRepository: TranslationRepository) =
        GetTranslatedTextUseCase(translationRepository = translationRepository)
}