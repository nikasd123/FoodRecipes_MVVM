package com.tz.fooddelivery.use_cases

import com.tz.fooddelivery.domain.models.MealItem
import com.tz.fooddelivery.domain.repository.MealsRepository
import com.tz.fooddelivery.domain.use_cases.GetMealsUseCase
import com.tz.fooddelivery.domain.use_cases.GetTranslatedTextUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class GetMealsUseCaseTest {
    private lateinit var mealsRepository: MealsRepository
    private lateinit var getTranslatedTextUseCase: GetTranslatedTextUseCase
    private lateinit var getMealsUseCase: GetMealsUseCase

    @Before
    fun setup(){
        mealsRepository = mock(MealsRepository::class.java)
        getTranslatedTextUseCase = mock(GetTranslatedTextUseCase::class.java)
        getMealsUseCase = GetMealsUseCase(mealsRepository, getTranslatedTextUseCase)
    }

    @Test
    fun `getMeals returns translated meals`() = runBlocking {
        val meals = listOf(MealItem("1", "image_url", "beef", "meat", "description"))
        val translatedMeals = listOf(MealItem("1", "image_url", "говядина", "meat", "описание"))

        `when`(mealsRepository.getMeals()).thenReturn(meals)
        `when`(getTranslatedTextUseCase(meals[0].title)).thenReturn(translatedMeals[0].title)
        `when`(getTranslatedTextUseCase(meals[0].description)).thenReturn(translatedMeals[0].description)

        val result = getMealsUseCase.getMeals()

        verify(mealsRepository).getMeals()
        verify(getTranslatedTextUseCase).invoke(meals[0].title)
        verify(getTranslatedTextUseCase).invoke(meals[0].description)
        assertEquals(translatedMeals, result)
    }

    @Test
    fun `getMealsByCategory returns translated meals`() = runBlocking {
        val category = "мясо"
        val englishCategory = "meat"
        val meals = listOf(MealItem("1", "image_url", "beef", "meat", "description"))
        val translatedMeals = listOf(MealItem("1", "image_url", "говядина", "meat", "description"))

        `when`(getTranslatedTextUseCase.getEnglishText(category)).thenReturn(englishCategory)
        `when`(mealsRepository.getMealsByCategory(englishCategory)).thenReturn(meals)
        `when`(getTranslatedTextUseCase(meals[0].title)).thenReturn(translatedMeals[0].title)

        val result = getMealsUseCase.getMealsByCategory(category)

        verify(getTranslatedTextUseCase).getEnglishText(category)
        verify(mealsRepository).getMealsByCategory(englishCategory)
        verify(getTranslatedTextUseCase).invoke(meals[0].title)
        assertEquals(translatedMeals, result)
    }


}