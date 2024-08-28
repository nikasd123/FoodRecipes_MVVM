package com.kaz4.domain.use_cases

import com.kaz4.domain.models.Category
import com.kaz4.domain.repository.MealsRepository

class GetCategoriesUseCase(
    private val mealsRepository: MealsRepository,
    private val getTranslatedTextUseCase: GetTranslatedTextUseCase
){
    suspend fun getCategories(): List<Category>? {
        val categories = mealsRepository.getCategories()
        return categories?.map { category ->
            val translatedCategoryName = getTranslatedTextUseCase(category.category)
            category.copy(category = translatedCategoryName)
        }
    }
}