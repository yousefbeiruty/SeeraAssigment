package com.yousef.seera.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yousef.seera.repository.ApiRepository
import com.yousef.seera.response.MoviesListResponse
import com.yousef.seera.utils.TypeOfCarusel
import retrofit2.HttpException

class MoviesPagingSource(
    private val repository: ApiRepository ,
    private val type: TypeOfCarusel
) : PagingSource<Int, MoviesListResponse.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesListResponse.Result> {
        return try {
            if(type==TypeOfCarusel.POPULAR)
                return getPopular(params)

                return getTopRated(params)
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    suspend fun getPopular(params: LoadParams<Int>): LoadResult<Int, MoviesListResponse.Result> {
        val currentPage = params.key ?: 1
        val response = repository.getPopularMoviesList(currentPage)
        val data = response.body()!!.results
        val responseData = mutableListOf<MoviesListResponse.Result>()
        responseData.addAll(data)

        return  LoadResult.Page(
            data = responseData,
            prevKey = if (currentPage == 1) null else -1,
            nextKey = currentPage.plus(1)
        )
    }

    suspend fun getTopRated(params: LoadParams<Int>): LoadResult<Int, MoviesListResponse.Result> {
        val currentPage = params.key ?: 1
        val response = repository.getTopRatedMoviesList(currentPage)
        val data = response.body()!!.results
        val responseData = mutableListOf<MoviesListResponse.Result>()
        responseData.addAll(data)

      return  LoadResult.Page(
            data = responseData,
            prevKey = if (currentPage == 1) null else -1,
            nextKey = currentPage.plus(1)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, MoviesListResponse.Result>): Int? {
        return null
    }


}