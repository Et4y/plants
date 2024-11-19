package com.softxpert.plants.data.repo_impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.softxpert.plants.data.end_point.PlantsEndPoint
import com.softxpert.plants.data.paging.PlantsPaging
import com.softxpert.plants.domain.model.plants.PlantModel
import com.softxpert.plants.domain.repo.PlantsRepository
import kotlinx.coroutines.flow.Flow
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


}