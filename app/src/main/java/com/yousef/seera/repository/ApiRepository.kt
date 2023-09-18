package com.yousef.seera.repository

import com.yousef.seera.api.ApiServices
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiServices: ApiServices,
) {
    suspend fun getPopularMoviesList(page: Int) = apiServices.getPopularMoviesList(page,"popularity.desc")
    suspend fun getMovieDetails(id: Int) = apiServices.getMovieDetails(id)

    suspend fun getTopRatedMoviesList(page: Int)=apiServices.getTopRatedMoviesList(page,"vote_average.desc")

    suspend fun getRevenueList(page: Int)=apiServices.getRevenueList(page,"revenue.desc")
}