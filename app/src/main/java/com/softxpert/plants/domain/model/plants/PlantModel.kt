package com.softxpert.plants.domain.model.plants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlantModel(
    val id: Int,
    @SerialName("common_name") val commonName: String?,
    val slug: String,
    @SerialName("scientific_name") val scientificName: String,
    val year: Int,
    val bibliography: String,
    val author: String,
    val status: String,
    val rank: String,
    @SerialName("family_common_name") val familyCommonName: String?,
    @SerialName("genus_id") val genusId: Int,
    @SerialName("image_url") val imageUrl: String?,
    val synonyms: List<String>,
    val genus: String,
    val family: String,
    val links: Links
)

@Serializable
data class Links(
    val self: String,
    val plant: String,
    val genus: String
)