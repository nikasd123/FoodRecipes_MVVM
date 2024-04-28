package com.tz.fooddelivery

import com.tz.fooddelivery.domain.models.MealRecipe
import com.tz.fooddelivery.domain.repository.RecipeRepository
import com.tz.fooddelivery.domain.use_cases.GetMealRecipeUseCase
import com.tz.fooddelivery.domain.use_cases.GetTranslatedTextUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class GetMealRecipeUseCaseTest {
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var getTranslatedTextUseCase: GetTranslatedTextUseCase
    private lateinit var getMealRecipeUseCaseTest: GetMealRecipeUseCase

    @Before
    fun setup(){
        recipeRepository = mock(RecipeRepository::class.java)
        getTranslatedTextUseCase = mock(GetTranslatedTextUseCase::class.java)
        getMealRecipeUseCaseTest = GetMealRecipeUseCase(recipeRepository, getTranslatedTextUseCase)
    }

    @Test
    fun `getMealRecipe returns translated recipe`() = runBlocking {
        val mealId = "1"
        val mealRecipe = MealRecipe(
            idMeal = "1",
            mealTitle = "beef",
            mealCategory = "meat",
            area = "USA",
            mealRecipe = "recipe",
            mealImage = "image_url",
            strYoutube = "youtube_url",
            ingredients = "ingredients"
        )
        val translatedMealRecipe = MealRecipe(
            idMeal = "1",
            mealTitle = "говядина",
            mealCategory = "meat",
            area = "USA",
            mealRecipe = "рецепт",
            mealImage = "image_url",
            strYoutube = "youtube_url",
            ingredients = "ингредиенты"
        )

        `when`(recipeRepository.getMealRecipe(mealId)).thenReturn(mealRecipe)
        `when`(getTranslatedTextUseCase(mealRecipe.mealTitle)).thenReturn(translatedMealRecipe.mealTitle)
        `when`(getTranslatedTextUseCase(mealRecipe.mealRecipe)).thenReturn(translatedMealRecipe.mealRecipe)
        `when`(getTranslatedTextUseCase(mealRecipe.ingredients)).thenReturn(translatedMealRecipe.ingredients)

        val result = getMealRecipeUseCaseTest.getMealRecipe(mealId)

        verify(recipeRepository).getMealRecipe(mealId)
        verify(getTranslatedTextUseCase).invoke(mealRecipe.mealTitle)
        verify(getTranslatedTextUseCase).invoke(mealRecipe.mealRecipe)
        verify(getTranslatedTextUseCase).invoke(mealRecipe.ingredients)
        assertEquals(translatedMealRecipe, result)
    }

    @Test
    fun `getMealRecipe returns null when repository returns null`() = runBlocking {
        val mealId = "1"
        `when`(recipeRepository.getMealRecipe(mealId)).thenReturn(null)

        val result = getMealRecipeUseCaseTest.getMealRecipe(mealId)

        verify(recipeRepository).getMealRecipe(mealId)
        assertNull(result)
    }

}