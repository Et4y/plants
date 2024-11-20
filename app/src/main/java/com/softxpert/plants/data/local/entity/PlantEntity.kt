package com.softxpert.plants.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import androidx.room.ColumnInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = true) val id: Int, // Primary key
    @ColumnInfo(name = "common_name") val commonName: String?,
    @ColumnInfo(name = "slug") val slug: String?,
    @ColumnInfo(name = "scientific_name") val scientificName: String,
    @ColumnInfo(name = "year") val year: Int?,
    @ColumnInfo(name = "bibliography") val bibliography: String?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "rank") val rank: String?,
    @ColumnInfo(name = "family_common_name") val familyCommonName: String?,
    @ColumnInfo(name = "genus_id") val genusId: Int?,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "genus") val genus: String?,
    @ColumnInfo(name = "family") val family: String?,
)


