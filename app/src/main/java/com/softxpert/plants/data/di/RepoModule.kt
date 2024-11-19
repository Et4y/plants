package com.softxpert.plants.data.di

import com.softxpert.plants.data.end_point.PlantsEndPoint
import com.softxpert.plants.data.repo_impl.PlantsRepositoryImpl
import com.softxpert.plants.domain.repo.PlantsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {


    @Provides
    @Singleton
    fun providePlantsRepo(
        plantsEndPoint: PlantsEndPoint
    ): PlantsRepository = PlantsRepositoryImpl(
        plantsEndPoint
    )


}