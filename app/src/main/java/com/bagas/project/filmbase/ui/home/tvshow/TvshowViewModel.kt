package com.bagas.project.filmbase.ui.home.tvshow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.remote.ApiConfig
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.data.responses.AiringTodayTvshowItem
import com.bagas.project.filmbase.data.responses.AiringTodayTvshowResponse
import com.bagas.project.filmbase.data.responses.TopRatedTvshowItem
import com.bagas.project.filmbase.data.responses.TopRatedTvshowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvshowViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getAiringTodayTvshow()
        getTopRatedTvshow()
    }

    fun getAiringTodayTvshow() = movieRepository.getAiringTodayTv()

    fun getTopRatedTvshow() = movieRepository.getTopRatedTvshow()

}