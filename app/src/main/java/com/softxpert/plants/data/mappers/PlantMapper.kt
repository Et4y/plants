package com.softxpert.plants.data.mappers

import com.softxpert.plants.data.local.entity.PlantEntity
import com.softxpert.plants.domain.model.plants.Links
import com.softxpert.plants.domain.model.plants.PlantModel

// Extension function to map PlantEntity to PlantModel
fun PlantEntity.toPlantModel(synonyms: List<String>, links: Links): PlantModel {
    return PlantModel(
        id = this.id,
        commonName = this.commonName,
        slug = this.slug,
        scientificName = this.scientificName,
        year = this.year,
        bibliography = this.bibliography,
        author = this.author,
        status = this.status,
        rank = this.rank,
        familyCommonName = this.familyCommonName,
        genusId = this.genusId,
        imageUrl = this.imageUrl,
        genus = this.genus,
        family = this.family,
        synonyms = synonyms,
        links = links
    )
}

// Extension function to map PlantModel to PlantEntity
fun PlantModel.toPlantEntity(): PlantEntity {
    return PlantEntity(
        id = this.id,
        commonName = this.commonName,
        slug = this.slug,
        scientificName = this.scientificName,
        year = this.year,
        bibliography = this.bibliography,
        author = this.author,
        status = this.status,
        rank = this.rank,
        familyCommonName = this.familyCommonName,
        genusId = this.genusId,
        imageUrl = this.imageUrl,
        genus = this.genus,
        family = this.family
    )
}
