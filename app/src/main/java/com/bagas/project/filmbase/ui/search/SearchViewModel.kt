package com.bagas.project.filmbase.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.entities.TrendingMovieEntity
import com.bagas.project.filmbase.data.local.entities.TrendingTvshowEntity
import com.bagas.project.filmbase.data.remote.ApiConfig
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.data.repository.MovieRepositoryImpl
import com.bagas.project.filmbase.data.responses.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _listMovieSearch = MutableLiveData<List<MovieSearchItem?>>()
    val listMovieSearch: LiveData<List<MovieSearchItem?>> = _listMovieSearch

    private val _listTvshowSearch = MutableLiveData<List<TvshowSearchItem?>>()
    val listTvshowSearch: LiveData<List<TvshowSearchItem?>> = _listTvshowSearch

    private val _trendingMovies = MutableLiveData<Result<List<TrendingMovieEntity?>>>()
    val trendingMovies: LiveData<Result<List<TrendingMovieEntity?>>> = _trendingMovies

    private val _trendingTvshows = MutableLiveData<Result<List<TrendingTvshowEntity?>>>()
    val trendingTvshows: LiveData<Result<List<TrendingTvshowEntity?>>> = _trendingTvshows

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "SearchViewModel"
    }

    init {
        getTrendingMovies()
        getTrendingTvshow()
    }

    fun getTrendingMovies() {
        viewModelScope.launch {
            movieRepository.getTrendingMovies().collectLatest {
                _trendingMovies.postValue(it)
            }
        }
    }

    fun getTrendingTvshow() {
        viewModelScope.launch {
            movieRepository.getTrendingTvshows().collectLatest {
                _trendingTvshows.postValue(it)
            }
        }
    }

    fun getMovieSearch(query: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMovieSearch(BuildConfig.API_KEY, query.toString().trim())
        client.enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listMovieSearch.postValue(responseBody.results!!)
                    } else {
                        Log.d(TAG, "Tidak ditemukan")
                    }
                } else {
                    Log.d(TAG, "message : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
    }

    fun getTvshowSearch(query: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getTvshowSearch(BuildConfig.API_KEY, query.toString().trim())
        client.enqueue(object : Callback<TvshowSearchResponse> {
            override fun onResponse(
                call: Call<TvshowSearchResponse>,
                response: Response<TvshowSearchResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listTvshowSearch.postValue(responseBody.results!!)
                    } else {
                        Log.d(TAG, "Tidak ditemukan")
                    }
                } else {
                    Log.d(TAG, "message : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TvshowSearchResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
    }
}