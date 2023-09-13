package com.yousef.seera.api

import com.yousef.seera.response.MovieDetailsResponse
import com.yousef.seera.response.MoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("movie/popular")
    suspend fun getPopularMoviesList(@Query("page") page: Int): Response<MoviesListResponse>

    @GET("movie/{movie_id}")
    suspend

    fun getMovieDetails(@Path("movie_id") id: Int): Response<MovieDetailsResponse>



}