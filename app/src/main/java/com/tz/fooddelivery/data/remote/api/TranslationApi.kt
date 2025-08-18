package com.tz.fooddelivery.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApi {
    @GET("/translate")
    suspend fun translateText(
        @Query("text") text: String,
        @Query("sl") sourceLanguage: String,
        @Query("dl") destinationLanguage: String,
    ): TranslationResponseDto
}

data class TranslationResponseDto(val translatedText: String)