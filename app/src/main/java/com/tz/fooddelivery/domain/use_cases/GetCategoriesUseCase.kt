package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.Category
import com.tz.fooddelivery.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun getCategories(): Flow<Result<List<Category>, NetworkError>> {
        return categoryRepository.getCategories().map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data)
                is Result.Error -> Result.Error(mapRepositoryError(result.error))
            }
        }
    }

    private fun mapRepositoryError(error: DataError): NetworkError {
        return when (error) {
            DataError.Local.DATABASE_ERROR -> NetworkError.DATA_NOT_FOUND
            else -> NetworkError.UNKNOWN_ERROR
        }
    }
}