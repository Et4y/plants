package com.softxpert.plants.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.softxpert.plants.data.local.dao.PlantDao
import com.softxpert.plants.data.local.dao.RemoteKeysDao
import com.softxpert.plants.data.local.entity.PlantEntity
import com.softxpert.plants.data.local.entity.RemoteKey

@Database(
    entities = [PlantEntity::class,RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao
    abstract fun remoteKeysDao() : RemoteKeysDao

}
