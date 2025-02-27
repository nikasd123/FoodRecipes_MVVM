package com.tz.fooddelivery.domain.common

sealed interface Error

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        DATA_NOT_FOUND,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        DATABASE_ERROR,
        UNKNOWN
    }
}

enum class NetworkError : Error {
    NETWORK_ERROR,
    DATA_NOT_FOUND,
    UNKNOWN_ERROR
}

enum class TranslationError : Error {
    NETWORK_ERROR,
    UNKNOWN_ERROR
}