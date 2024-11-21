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
               1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        return try {
            // Fetch data from the remote service
            val apiResponse = service.getPlants(page)
            val repos = apiResponse.data
            val endOfPaginationReached = repos.isEmpty()

            repoDatabase.withTransaction {
                // Clear old data on REFRESH
                if (loadType == LoadType.REFRESH) {
                    repoDatabase.remoteKeysDao().clearKeys()
                    repoDatabase.plantDao().clearAll()
                }

                // Insert new data with keys
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKey(repoId = it.id.toLong(), prevKey = prevKey, nextKey = nextKey)
                }
                repoDatabase.remoteKeysDao().insertKey(keys)
                repoDatabase.plantDao().insertPlants(repos.map { it.toPlantEntity() })
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PlantEntity>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { repo ->
                repoDatabase.remoteKeysDao().getKeyByMovie(repo.id.toString())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PlantEntity>): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
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
