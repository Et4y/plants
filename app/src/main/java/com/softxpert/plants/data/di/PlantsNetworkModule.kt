package com.softxpert.plants.data.di

import com.softxpert.plants.data.end_point.PlantsEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlantsNetworkModule {

    @Singleton
    @Provides
    fun providesPlantsApi(retrofit: Retrofit): PlantsEndPoint {
        return retrofit.create(PlantsEndPoint::class.java)
    }

}