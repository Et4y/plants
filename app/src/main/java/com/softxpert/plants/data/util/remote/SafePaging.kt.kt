package com.softxpert.plants.data.util.remote

import androidx.paging.PagingSource
import com.softxpert.plants.domain.model.pagination.ApiResponse
import com.softxpert.plants.domain.util.NetworkExceptions
import retrofit2.HttpException
import java.io.IOException

/**
 * A utility function to handle paginated responses from an API for Paging 3.


 * **Benefits:**
 * - **Centralized Pagination Handling**: This function centralizes the pagination logic,
     ensuring consistent pagination handling across different parts of the application.
 * - **Error Handling**: Automatically handles various types of errors such as network
     issues (IOException), HTTP errors (HttpException), and general exceptions,
     making the pagination process more robust and fault-tolerant.
 * - **Efficiency**: It optimizes the pagination flow by ensuring that only the necessary
     data is loaded (e.g., the next page of data), which is ideal for large datasets and
     provides a better user experience.
 * - **Ease of Use**: By abstracting the pagination logic into a utility function, it
     simplifies the implementation of pagination in repositories and makes the code
     more maintainable.

 * **Parameters:**
 * - pageIndex: The current page number being requested. It is used to determine if
      there are more pages to load.
 * - response: The API response containing the data for the current page. It should
      follow the ApiResponse<T> structure, which includes the data to be displayed.
 * - currentList: The list of items currently loaded. While this is not utilized
      in the function directly, it may be useful for advanced scenarios where
      maintaining the previous state of the list is necessary.
 * - totalPages: The total number of pages available from the API. It helps
      determine whether more pages need to be fetched.

 * **Returns:**
 * - A PagingSource.LoadResult that Paging 3 uses to load the data, which can be
 *   a success (LoadResult.Page) or an error (LoadResult.Error).

 * **Error Handling:**
 * - IOException: Handles network-related issues like no internet connection.
 * - HttpException: Catches HTTP-specific errors such as 404, 500, etc.
 * - General exceptions are caught and returned as errors.

 * Example usage:
 * kotlin
 * fun <T : Any> fetchDataFromApi(pageIndex: Int): PagingSource.LoadResult<Int, T> {
 *     val apiResponse = apiService.getPaginatedData(pageIndex)
 *     return handlePagingResponse(
 *         pageIndex = pageIndex,
 *         response = apiResponse,
 *         currentList = currentDataList,
 *         totalPages = apiResponse.totalPages
 *     )
 * }
 * 
 */

fun <T : Any> handlePagingResponse(
    pageIndex: Int,
    response: ApiResponse<T>,
    currentList: List<T>?,
    totalPages: Int
): PagingSource.LoadResult<Int, T> {
    return try {

        // Determine if there are more pages
        val hasMorePages = pageIndex < totalPages

        // Success case
        PagingSource.LoadResult.Page(
            data = response.data,
            prevKey = if (pageIndex == 1) null else pageIndex - 1, // No previous page on the first page
            nextKey = if (hasMorePages) pageIndex + 1 else null    // No next page if on the last page
        )

    } catch (exception: IOException) {
        // Network error
        PagingSource.LoadResult.Error(NetworkExceptions.ConnectionException)
    } catch (exception: HttpException) {
        // HTTP error
        PagingSource.LoadResult.Error(exception)
    } catch (exception: Exception) {
        // Other errors
        PagingSource.LoadResult.Error(exception)
    }

}

