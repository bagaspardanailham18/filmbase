package com.bagas.project.filmbase.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.data.responses.UpcomingMoviesResponse

class SeeAllViewModel: ViewModel() {

    init {
        getUpcomingMovies()
        getTopRatedMovies()
    }

    private fun getUpcomingMovies() {

    }

    private fun getTopRatedMovies() {

    }

}