package com.softxpert.plants.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.softxpert.plants.data.end_point.PlantsEndPoint
import com.softxpert.plants.data.local.AppDatabase
import com.softxpert.plants.data.local.entity.PlantEntity
import com.softxpert.plants.data.local.entity.RemoteKey
import com.softxpert.plants.data.mappers.toPlantEntity
import retrofit2.HttpException
import java.io.IOException



@OptIn(ExperimentalPagingApi::class)
class PlantsRemoteMediator(
    private val service: PlantsEndPoint,
    private val repoDatabase: AppDatabase
) : RemoteMediator<Int, PlantEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PlantEntity>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }


        try {
            val apiResponse = service.getPlants( page)

            val repos = apiResponse.data
            val endOfPaginationReached = repos.isEmpty()
            repoDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    repoDatabase.remoteKeysDao().clearKeys()
                    repoDatabase.plantDao().clearAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKey(repoId = it.id.toLong(), prevKey = prevKey, nextKey = nextKey)
                }
                repoDatabase.remoteKeysDao().insertKey(keys)
                repoDatabase.plantDao().insertPlants(repos.map { it.toPlantEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PlantEntity>): RemoteKey? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                repoDatabase.remoteKeysDao().getKeyByMovie(repo.id.toString())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PlantEntity>): RemoteKey? {
     
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                repoDatabase.remoteKeysDao().getKeyByMovie(repo.id.toString())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PlantEntity>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                repoDatabase.remoteKeysDao().getKeyByMovie(repoId.toString())
            }
        }
    }
}