package com.bagas.project.filmbase.ui.home.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagas.project.filmbase.data.local.entities.AiringTodayTvEntity
import com.bagas.project.filmbase.data.local.entities.TopRatedTvEntity
import com.bagas.project.filmbase.data.local.entities.UpcomingMovieEntity
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.data.repository.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvshowViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _airingTodayTvshows = MutableLiveData<com.bagas.project.filmbase.data.Result<List<AiringTodayTvEntity>>>()
    val airingTodayTvshows: LiveData<com.bagas.project.filmbase.data.Result<List<AiringTodayTvEntity>>> get() = _airingTodayTvshows

    private val _topRatedTvshows = MutableLiveData<com.bagas.project.filmbase.data.Result<List<TopRatedTvEntity>>>()
    val topRatedTvshows: LiveData<com.bagas.project.filmbase.data.Result<List<TopRatedTvEntity>>> get() = _topRatedTvshows

    init {
        getAiringTodayTvshow()
        getTopRatedTvshow()
    }

    fun getAiringTodayTvshow() {
        viewModelScope.launch {
            movieRepository.getAiringTodayTv().collectLatest {
                _airingTodayTvshows.postValue(it)
            }
        }
    }

    fun getTopRatedTvshow() {
        viewModelScope.launch {
            movieRepository.getTopRatedTvshow().collectLatest {
                _topRatedTvshows.postValue(it)
            }
        }
    }

}