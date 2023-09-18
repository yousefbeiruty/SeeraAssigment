package com.yousef.seera.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.yousef.seera.paging.GenericPagingSource
import com.yousef.seera.repository.ApiRepository
import com.yousef.seera.response.MovieDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val moviesList = Pager(PagingConfig(1)) {
        GenericPagingSource(repository) { page ->
            val response = repository.getPopularMoviesList(page)
            response.body()?.results ?: emptyList()
        }
    }.flow.cachedIn(viewModelScope)

    val listTopRated = Pager(PagingConfig(1)) {
        GenericPagingSource(repository) { page ->
            val response = repository.getTopRatedMoviesList(page)
            response.body()?.results ?: emptyList()
        }
    }.flow.cachedIn(viewModelScope)

    val revenueList=Pager(PagingConfig(1)) {
        GenericPagingSource(repository) { page ->
            val response = repository.getRevenueList(page)
            response.body()?.results ?: emptyList()
        }
    }.flow.cachedIn(viewModelScope)
    //Api
    val detailsMovie = MutableLiveData<MovieDetailsResponse>()
    fun loadDetailsMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getMovieDetails(id)
        if (response.isSuccessful) {
            detailsMovie.postValue(response.body())
        }
        loading.postValue(false)
    }
}