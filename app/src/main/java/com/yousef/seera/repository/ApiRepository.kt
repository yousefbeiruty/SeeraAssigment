package com.yousef.seera.repository

import com.yousef.seera.api.ApiServices
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiServices: ApiServices,
) {
    suspend fun getPopularMoviesList(page: Int) = apiServices.getPopularMoviesList(page)
    suspend fun getMovieDetails(id: Int) = apiServices.getMovieDetails(id)

    suspend fun getTopRatedMoviesList(page: Int)=apiServices.getTopRatedMoviesList(page)
}