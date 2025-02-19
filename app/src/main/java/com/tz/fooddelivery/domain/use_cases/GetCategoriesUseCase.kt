package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val translateUseCase: GetTranslatedTextUseCase
) {
    suspend fun getCategories(): List<Category>? {
        val categories = mealsRepository.getCategories() ?: return null

        return coroutineScope {
            categories.map { category ->
                async(Dispatchers.IO) {
                    val translatedName = translateUseCase(category.category)
                    category.copy(category = translatedName)
                }
            }.awaitAll()
        }
    }

    fun getCategoriesFlow(): Flow<List<Category?>> = flow {
        val rawCategories = mealsRepository.getCategories() ?: emptyList()

        val translatedCategories = translateUseCase
            .translateFlow(rawCategories.map { it.category })
            .map { (original, translated) ->
                rawCategories.find { it.category == original }?.copy(category = translated)
            }
            .toList()

        emit(translatedCategories)
    }
}