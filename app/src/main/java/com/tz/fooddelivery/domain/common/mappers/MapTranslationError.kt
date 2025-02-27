package com.tz.fooddelivery.domain.common.mappers

import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.use_cases.GetTranslatedTextUseCase

internal fun mapTranslationError(e: Exception): NetworkError {
    return when (e) {
        is GetTranslatedTextUseCase.TranslationException -> e.error
        else -> NetworkError.UNKNOWN_ERROR
    }
}