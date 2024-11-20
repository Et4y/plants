package com.softxpert.plants.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.softxpert.plants.data.end_point.PlantsEndPoint
import com.softxpert.plants.data.util.handlePagingResponse
import com.softxpert.plants.domain.model.plants.PlantModel

class PlantsPaging(
    private val apiService: PlantsEndPoint
) : PagingSource<Int, PlantModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlantModel> {
        val page = params.key ?: 1

        return try {
            // Fetch the response from the API
            val response = apiService.getPlants(page = page)

            val speciesList = response.data // assuming response contains the list of species
            val nextPage = if (page < response.meta.total) page + 1 else null

            return handlePagingResponse(
                nextPage!!,
                response,
                response.data,
                response.links
            )
//            LoadResult.Page(
//                data = speciesList,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = nextPage
//            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PlantModel>): Int? {
        // Return the key for refreshing the data.
        return state.anchorPosition
    }
}
