package com.tz.fooddelivery.data.remote.api

import com.tz.fooddelivery.data.remote.dto.TranslationResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApi {
    @GET("/translate")
    suspend fun translateText(
        @Query("sl") sourceLanguage: String,
        @Query("dl") destinationLanguage: String,
        @Query("text") text: String
    ): TranslationResponseDto
}