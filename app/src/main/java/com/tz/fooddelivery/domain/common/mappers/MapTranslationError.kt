package com.tz.fooddelivery.domain.common.mappers

import com.tz.fooddelivery.domain.common.NetworkError
import com.tz.fooddelivery.domain.use_cases.GetTranslatedTextUseCase
import java.io.IOException

internal fun mapTranslationError(e: Exception): NetworkError = when (e) {
    is GetTranslatedTextUseCase.TranslationException -> e.error
    is IOException -> NetworkError.NETWORK_ERROR
    else -> NetworkError.UNKNOWN_ERROR
}