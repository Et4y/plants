package com.softxpert.plants.data.di

//import com.softxpert.plants.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@InstallIn(SingletonComponent::class)
@Module
object StringsModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RemoteBaseUrl


    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "BuildConfig.REMOTE_URL"

    @Provides
    @RemoteBaseUrl
    fun provideRemoteBaseUrl(@BaseUrl baseUrl: String): String = "$baseUrl/api/v1/"


}
