package com.softxpert.plants.data.di

import android.content.Context
import androidx.room.Room
import com.softxpert.plants.data.local.AppDatabase
import com.softxpert.plants.data.local.dao.PlantDao
import com.softxpert.plants.data.local.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {

        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .build()
    }

    @Provides
    fun providePlantDao(database: AppDatabase): PlantDao {
        return database.plantDao()
    }


    @Provides
    fun provideRemoteKeys(database: AppDatabase): RemoteKeysDao {
        return database.remoteKeysDao()
    }


}
