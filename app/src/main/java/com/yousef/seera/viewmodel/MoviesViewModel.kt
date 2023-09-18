package com.yousef.seera.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.yousef.seera.paging.MoviesPagingSource
import com.yousef.seera.repository.ApiRepository
import com.yousef.seera.response.MovieDetailsResponse
import com.yousef.seera.utils.TypeOfCarusel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val moviesList = Pager(PagingConfig(1)) {
        MoviesPagingSource(repository,TypeOfCarusel.POPULAR)
    }.flow.cachedIn(viewModelScope)

    val listTopRated=Pager(PagingConfig(1)) {
        MoviesPagingSource(repository,TypeOfCarusel.TOP_RATED)
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