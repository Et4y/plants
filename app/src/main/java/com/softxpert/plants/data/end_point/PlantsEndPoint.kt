package com.softxpert.plants.data.end_point

import com.softxpert.plants.domain.model.pagination.ApiResponse
import com.softxpert.plants.domain.model.plants.PlantModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlantsEndPoint {


    @GET("plants?token=v4dMs4EpiMDWD3GuMTA5dMT8w-W8O3kPyrx6iX6EbYE")
    suspend fun getPlants(
        @Query("page") page: Int
    ): ApiResponse<PlantModel>

    @GET("distributions/{id}/plants?token=v4dMs4EpiMDWD3GuMTA5dMT8w-W8O3kPyrx6iX6EbYE")
    suspend fun getFilteredPlants(
        @Path("id") id: String,
        @Query("page") page: Int,
    ): ApiResponse<PlantModel>

}