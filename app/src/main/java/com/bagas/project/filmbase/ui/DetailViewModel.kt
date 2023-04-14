package com.bagas.project.filmbase.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagas.project.filmbase.data.local.entities.FavoriteMovieEntity
import com.bagas.project.filmbase.data.local.entities.FavoriteTvEntity
import com.bagas.project.filmbase.data.repository.MovieRepositoryImpl
import com.bagas.project.filmbase.data.responses.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.repository.LocalRepository
import com.bagas.project.filmbase.data.repository.MovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository, private val localRepository: LocalRepository) : ViewModel() {

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

    fun getFavoritedMovieById(id: Int?) = localRepository.getFavoriteMovieById(id!!)

    fun insertFavoritedMovie(movie: FavoriteMovieEntity?) {
        viewModelScope.launch {
            localRepository.insertFavoriteMovie(movie!!)
        }
    }

    fun deleteFavoritedMovie(movie: FavoriteMovieEntity?) {
        viewModelScope.launch {
            localRepository.deleteFavoriteMovie(movie!!)
        }
    }

    fun getFavoritedTvById(id: Int?) = localRepository.getFavoriteTvById(id!!)

    fun insertFavoritedTv(tv: FavoriteTvEntity?) {
        viewModelScope.launch {
            localRepository.insertFavoriteTvshow(tv!!)
        }
    }

    fun deleteFavoritedTv(tv: FavoriteTvEntity?) {
        viewModelScope.launch {
            localRepository.deleteFavoriteTvshow(tv!!)
        }
    }

}