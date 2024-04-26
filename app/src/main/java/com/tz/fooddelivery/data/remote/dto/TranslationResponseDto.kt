package com.tz.fooddelivery.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.tz.fooddelivery.domain.models.TranslationResponse

data class TranslationResponseDto(
    @SerializedName("destination-text") val translatedText: String?
)

internal fun TranslationResponseDto.toDomain(): TranslationResponse =
    TranslationResponse(
        translatedText = translatedText ?: ""
    )