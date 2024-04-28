package com.tz.fooddelivery.use_cases

import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.repository.MealsRepository
import com.tz.fooddelivery.domain.use_cases.GetCategoriesUseCase
import com.tz.fooddelivery.domain.use_cases.GetTranslatedTextUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class GetCategoriesUseCaseTest {
    private lateinit var mealsRepository: MealsRepository
    private lateinit var getTranslatedTextUseCase: GetTranslatedTextUseCase
    private lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @Before
    fun setup() {
        mealsRepository = mock(MealsRepository::class.java)
        getTranslatedTextUseCase = mock(GetTranslatedTextUseCase::class.java)
        getCategoriesUseCase = GetCategoriesUseCase(mealsRepository, getTranslatedTextUseCase)
    }

    @Test
    fun `getCategories returns translated categories`() = runBlocking {
        val categories = listOf(Category("1", "beef", false), Category("2", "chicken", false))
        val translatedCategories = listOf(Category("1", "говядина", false), Category("2", "курица", false))

        `when`(mealsRepository.getCategories()).thenReturn(categories)
        `when`(getTranslatedTextUseCase("beef")).thenReturn("говядина")
        `when`(getTranslatedTextUseCase("chicken")).thenReturn("курица")

        val result = getCategoriesUseCase.getCategories()

        verify(mealsRepository).getCategories()
        verify(getTranslatedTextUseCase).invoke("beef")
        verify(getTranslatedTextUseCase).invoke("chicken")
        assertEquals(translatedCategories, result)
    }

}