package com.softxpert.plants.data.util

import android.util.Log
import androidx.paging.PagingSource
import com.softxpert.plants.domain.model.pagination.ApiResponse
import com.softxpert.plants.domain.model.pagination.PaginationLinks
import com.softxpert.plants.domain.util.NetworkExceptions
import retrofit2.HttpException
import java.io.IOException

fun <T : Any> handlePagingResponse(
    pageIndex: Int,
    response: ApiResponse<*>,
    list: List<T>?,
    pagination: PaginationLinks?
): PagingSource.LoadResult<Int, T> {

    return try {

        val nextKey = if (pagination?.next == null) {
            null
        } else {
            // By default, initial load size = 3 * NETWORK PAGE SIZE
            // ensure we're not requesting duplicating items at the 2nd request
            pageIndex + 1
        }

        PagingSource.LoadResult.Page(
            data = list!!,
            prevKey = if (pageIndex == 1) null else pageIndex,
            nextKey = nextKey
        )

    } catch (exception: IOException) {
        Log.i("networkError", "handlePostsAdsPagingResponse:IOException ")
        return PagingSource.LoadResult.Error(NetworkExceptions.ConnectionException)
    } catch (exception: HttpException) {
        Log.i("networkError", "handlePostsAdsPagingResponse:HttpException ")
        return PagingSource.LoadResult.Error(exception)
    } catch (exception: Exception) {
        Log.i("networkError", "handlePostsAdsPagingResponse:Exception ")
        return PagingSource.LoadResult.Error(exception)
    }
}
