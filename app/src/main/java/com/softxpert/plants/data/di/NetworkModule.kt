package com.softxpert.plants.data.di


import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {


    @Provides
    @Singleton
    fun converterFactory(): Converter.Factory {
        val json = Json { ignoreUnknownKeys = true
            isLenient = true }
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: LoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory:Converter.Factory,
        @StringsModule.RemoteBaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

}
