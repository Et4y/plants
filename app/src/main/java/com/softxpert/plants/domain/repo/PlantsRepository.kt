package com.softxpert.plants.domain.repo

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.softxpert.plants.domain.model.plants.PlantModel
import com.softxpert.plants.domain.util.UiState
import kotlinx.coroutines.flow.Flow

interface PlantsRepository {

     fun getPlants(): Flow<PagingData<PlantModel>>

     fun getFilterPlants(id : String): Flow<PagingData<PlantModel>>

}