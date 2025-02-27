package com.tz.fooddelivery.domain.common.mappers

import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.tz.fooddelivery.domain.common.DataError
import retrofit2.HttpException
import java.io.IOException

internal fun mapError(e: Exception): DataError =
    when (e) {
        is HttpException -> when (e.code()) {
            408 -> DataError.Network.REQUEST_TIMEOUT
            429 -> DataError.Network.TOO_MANY_REQUESTS
            413 -> DataError.Network.PAYLOAD_TOO_LARGE
            else -> DataError.Network.UNKNOWN
        }
        is IOException -> DataError.Network.NO_INTERNET
        is JsonSyntaxException, is JsonParseException -> DataError.Network.SERIALIZATION
        else -> DataError.Network.UNKNOWN
    }

internal fun mapLocalError(e: Exception): DataError.Local =
    when (e) {
        is IOException -> DataError.Local.DISK_FULL
        else -> DataError.Local.UNKNOWN
    }