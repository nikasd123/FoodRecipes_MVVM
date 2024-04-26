package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.repository.MealsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(
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