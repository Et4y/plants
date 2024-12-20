package com.softxpert.plants.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.softxpert.plants.data.end_point.PlantsEndPoint
import com.softxpert.plants.domain.model.plants.PlantModel

class PlantsPaging(
    private val apiService: PlantsEndPoint
) : PagingSource<Int, PlantModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlantModel> {
        val page = params.key ?: 1

        return try {
            // Fetch the response from the API
            val response = apiService.getPlants(page = page)

            val nextPage = if (page < response.meta.total) page + 1 else null

            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PlantModel>): Int? {
        return state.anchorPosition
    }
}