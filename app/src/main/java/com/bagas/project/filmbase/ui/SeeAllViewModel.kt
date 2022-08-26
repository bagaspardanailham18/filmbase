package com.bagas.project.filmbase.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.data.responses.UpcomingMoviesResponse

class SeeAllViewModel: ViewModel() {

    private val _listSeeAll = MutableLiveData<List<UpcomingMoviesResponse>>()
    val listSeeAll: LiveData<List<UpcomingMoviesResponse>> = _listSeeAll

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUpcomingMovies()
        getTopRatedMovies()
    }

    private fun getUpcomingMovies() {

    }

    private fun getTopRatedMovies() {

    }

}