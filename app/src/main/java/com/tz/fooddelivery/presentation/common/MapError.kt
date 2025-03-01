package com.tz.fooddelivery.presentation.common

import com.tz.fooddelivery.domain.common.NetworkError
import java.io.IOException

fun mapError(e: Exception): NetworkError =
    when (e) {
        is IOException -> NetworkError.NETWORK_ERROR
        is NullPointerException -> NetworkError.DATA_NOT_FOUND
        else -> NetworkError.UNKNOWN_ERROR
    }