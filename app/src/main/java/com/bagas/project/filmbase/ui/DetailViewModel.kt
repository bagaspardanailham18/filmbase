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
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _movieDetail = MutableLiveData<MovieDetailResponse?>()
    val movieDetail: LiveData<MovieDetailResponse?> = _movieDetail

    private val _movieVideos = MutableLiveData<List<MovieVideoItem?>>()
    val movieVideos: LiveData<List<MovieVideoItem?>> = _movieVideos

    private val _tvDetail = MutableLiveData<TvshowDetailResponse?>()
    val tvDetail: LiveData<TvshowDetailResponse?> = _tvDetail

    private val _tvVideos = MutableLiveData<List<TvVideoItem?>>()
    val tvVideos: LiveData<List<TvVideoItem?>> = _tvVideos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "DetailViewModel"
    }

    fun getMovieDetail(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMovieDetail(id, BuildConfig.API_KEY)
        client.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _movieDetail.postValue(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
    }

    fun getMovieVideos(id: Int?) {
        val client = ApiConfig.getApiService().getMovieVideos(id!!, BuildConfig.API_KEY)
        client.enqueue(object : Callback<MovieVideoResponse> {
            override fun onResponse(
                call: Call<MovieVideoResponse>,
                response: Response<MovieVideoResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _movieVideos.postValue(responseBody.results!!)
                    } else {
                        Log.d(TAG, "message : ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<MovieVideoResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
    }

    fun getTvshowDetail(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getTvDetail(id, BuildConfig.API_KEY)
        client.enqueue(object : Callback<TvshowDetailResponse> {
            override fun onResponse(
                call: Call<TvshowDetailResponse>,
                response: Response<TvshowDetailResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _tvDetail.postValue(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<TvshowDetailResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getTvVideos(id: Int?) {
        val client = ApiConfig.getApiService().getTvVideos(id!!, BuildConfig.API_KEY)
        client.enqueue(object : Callback<TvshowVideoResponse> {
            override fun onResponse(
                call: Call<TvshowVideoResponse>,
                response: Response<TvshowVideoResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _tvVideos.postValue(responseBody.results!!)
                    } else {
                        Log.d(TAG, "message : ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<TvshowVideoResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
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