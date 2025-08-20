package com.tz.fooddelivery.domain.use_cases

import com.tz.fooddelivery.domain.common.DataError
import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.common.Result
import com.tz.fooddelivery.domain.models.DishItem
import com.tz.fooddelivery.domain.repository.MealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton


@Singleton
class GetAllDishesUseCase (
    private val mealsRepository: MealsRepository
) {
    suspend fun getAllDishes() : Flow<Result<List<DishItem>, NetworkError> > {
        return mealsRepository.getAllDishes().map { result ->
            when(result){
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