package com.tz.fooddelivery.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TranslationResponseDto(
    @SerializedName("destination-text") val translatedText: String?
)