package com.bagas.project.filmbase.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.local.FavoriteMovieEntity
import com.bagas.project.filmbase.data.local.FavoriteTvEntity
import com.bagas.project.filmbase.data.remote.ApiConfig
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.data.responses.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import com.bagas.project.filmbase.data.Result
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movieDetail = MutableLiveData<Result<MovieDetailResponse?>>()
    val movieDetail: LiveData<Result<MovieDetailResponse?>> = _movieDetail

    private val _movieVideos = MutableLiveData<Result<MovieVideoResponse?>>()
    val movieVideos: LiveData<Result<MovieVideoResponse?>> = _movieVideos

    private val _tvDetail = MutableLiveData<Result<TvshowDetailResponse?>>()
    val tvDetail: LiveData<Result<TvshowDetailResponse?>> = _tvDetail

    private val _tvVideos = MutableLiveData<Result<TvshowVideoResponse?>>()
    val tvVideos: LiveData<Result<TvshowVideoResponse?>> = _tvVideos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "DetailViewModel"
    }

    suspend fun getMovieDetail(id: Int?) {
        movieRepository.getMovieDetail(id).collectLatest {
            _movieDetail.postValue(it)
        }
    }

    suspend fun getMovieVideos(id: Int?) {
        movieRepository.getMovieVideos(id).collectLatest {
            _movieVideos.postValue(it)
        }
    }

    suspend fun getTvshowDetail(id: Int?) {
        movieRepository.getTvshowDetail(id).collectLatest {
            _tvDetail.postValue(it)
        }
    }

    suspend fun getTvVideos(id: Int?) {
        movieRepository.getTvshowVideos(id).collectLatest {
            _tvVideos.postValue(it)
        }
    }

    fun getFavoritedMovieById(id: Int?) = movieRepository.getFavoriteMovieById(id!!)

    fun insertFavoritedMovie(movie: FavoriteMovieEntity?) {
        viewModelScope.launch {
            movieRepository.insertFavoriteMovie(movie!!)
        }
    }

    fun deleteFavoritedMovie(movie: FavoriteMovieEntity?) {
        viewModelScope.launch {
            movieRepository.deleteFavoriteMovie(movie!!)
        }
    }

    fun getFavoritedTvById(id: Int?) = movieRepository.getFavoriteTvById(id!!)

    fun insertFavoritedTv(tv: FavoriteTvEntity?) {
        viewModelScope.launch {
            movieRepository.insertFavoriteTvshow(tv!!)
        }
    }

    fun deleteFavoritedTv(tv: FavoriteTvEntity?) {
        viewModelScope.launch {
            movieRepository.deleteFavoriteTvshow(tv!!)
        }
    }

}