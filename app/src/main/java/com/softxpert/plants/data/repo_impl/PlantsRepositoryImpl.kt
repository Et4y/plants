package com.softxpert.plants.data.repo_impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.softxpert.plants.data.end_point.PlantsEndPoint
import com.softxpert.plants.data.local.AppDatabase
import com.softxpert.plants.data.local.entity.PlantEntity
import com.softxpert.plants.data.paging.FilterPlantsPaging
import com.softxpert.plants.data.paging.PlantsPaging
import com.softxpert.plants.domain.model.plants.PlantModel
import com.softxpert.plants.domain.repo.PlantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PlantsRepositoryImpl @Inject constructor(
    private val plantsEndPoint: PlantsEndPoint,
    private val appDatabase: AppDatabase
) : PlantsRepository {


    override fun getPlants(): Flow<PagingData<PlantModel>> = Pager(
        config = PagingConfig(
            pageSize = 20, // Number of items per page
            prefetchDistance = 5, // Fetch next page when 5 items away from end
            initialLoadSize = 20 // Initial load size (defaults to pageSize * 3)
        ),
        pagingSourceFactory =
        { PlantsPaging(plantsEndPoint) }
    ).flow


    override fun getFilterPlants(id: String): Flow<PagingData<PlantModel>> = Pager(
        config = PagingConfig(
            pageSize = 20, // Number of items per page
            prefetchDistance = 5, // Fetch next page when 5 items away from end
            initialLoadSize = 20 // Initial load size (defaults to pageSize * 3)
        ),
        pagingSourceFactory =
        { FilterPlantsPaging(plantsEndPoint, id) }
    ).flow


}
