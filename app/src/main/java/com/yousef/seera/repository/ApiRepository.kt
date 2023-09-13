package com.ezatpanah.seera.repository

import com.ezatpanah.seera.api.ApiServices
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiServices: ApiServices,
) {
    suspend fun getPopularMoviesList(page: Int) = apiServices.getPopularMoviesList(page)
    suspend fun getMovieDetails(id: Int) = apiServices.getMovieDetails(id)
}