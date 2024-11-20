package com.softxpert.plants.domain.repo

import androidx.paging.PagingData
import com.softxpert.plants.domain.model.plants.PlantModel
import kotlinx.coroutines.flow.Flow

interface PlantsRepository {

     fun getPlants(): Flow<PagingData<PlantModel>>

     fun getFilterPlants(id : String): Flow<PagingData<PlantModel>>

}