package com.softxpert.plants.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softxpert.plants.data.local.entity.RemoteKey

@Dao
interface RemoteKeysDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<RemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: List<RemoteKey>)

    @Query("select * from remote_keys where repoId=:key")
    suspend fun getKeyByMovie(key: String): RemoteKey?

    @Query("delete from remote_keys")
    suspend fun clearKeys()

}