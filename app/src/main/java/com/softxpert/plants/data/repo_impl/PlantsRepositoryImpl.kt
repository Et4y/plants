package com.softxpert.plants.data.repo_impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.softxpert.plants.data.end_point.PlantsEndPoint
import com.softxpert.plants.data.paging.FilterPlantsPaging
import com.softxpert.plants.data.paging.PlantsPaging
import com.softxpert.plants.domain.model.plants.PlantModel
import com.softxpert.plants.domain.repo.PlantsRepository
import com.softxpert.plants.domain.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class PlantsRepositoryImpl @Inject constructor(
    private val plantsEndPoint: PlantsEndPoint
) : PlantsRepository {


    override fun getPlants(): Flow<PagingData<PlantModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory =
        { PlantsPaging(plantsEndPoint) }
    ).flow


    override fun getFilterPlants(id : String): Flow<PagingData<PlantModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory =
        { FilterPlantsPaging(plantsEndPoint,id) }
    ).flow


}