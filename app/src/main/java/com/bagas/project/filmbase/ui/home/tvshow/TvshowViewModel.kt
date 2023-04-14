package com.bagas.project.filmbase.ui.home.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.data.repository.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvshowViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAiringTodayTvshow() = movieRepository.getAiringTodayTv()

    fun getTopRatedTvshow() = movieRepository.getTopRatedTvshow()

}