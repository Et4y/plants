package com.softxpert.plants.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val repoId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)