package com.softxpert.plants.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softxpert.plants.data.local.entity.PlantEntity

@Dao
interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlants(plants: List<PlantEntity>)


    @Query("SELECT * FROM plants")
    fun getAllPlants(): PagingSource<Int, PlantEntity>


    @Query("DELETE FROM plants")
    suspend fun clearAll()


}
