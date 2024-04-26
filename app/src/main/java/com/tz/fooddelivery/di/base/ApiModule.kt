package com.tz.fooddelivery.di.base

import com.tz.fooddelivery.data.remote.api.MealsApi
import com.tz.fooddelivery.data.remote.api.TranslationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val MEALS_BASE_URL = "https://themealdb.com/api/json/v1/1/"
    private const val TRANSLATION_BASE_URL = "https://ftapi.pythonanywhere.com/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    @Named("MealsRetrofit")
    fun provideMealsRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MEALS_BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    @Named("TranslationRetrofit")
    fun provideTranslationRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(TRANSLATION_BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideMealsApiService(@Named("MealsRetrofit") retrofit: Retrofit): MealsApi =
        retrofit.create(MealsApi::class.java)

    @Singleton
    @Provides
    fun provideTranslationApiService(@Named("TranslationRetrofit") retrofit: Retrofit): TranslationApi =
        retrofit.create(TranslationApi::class.java)
}

