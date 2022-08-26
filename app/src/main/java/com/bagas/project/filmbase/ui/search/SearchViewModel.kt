package com.bagas.project.filmbase.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.remote.ApiConfig
import com.bagas.project.filmbase.data.responses.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _listTrendingMovies = MutableLiveData<List<TrendingMoviesItem?>>()
    val listTrendingMovies: LiveData<List<TrendingMoviesItem?>> = _listTrendingMovies

    private val _listTrendingTvshow = MutableLiveData<List<TrendingTvshowItem?>>()
    val listTrendingTvshow: LiveData<List<TrendingTvshowItem?>> = _listTrendingTvshow

    private val _listSearch = MutableLiveData<List<MultiSearchItem?>>()
    val listSearch: LiveData<List<MultiSearchItem?>> = _listSearch

    private val _listMovieSearch = MutableLiveData<List<MovieSearchItem?>>()
    val listMovieSearch: LiveData<List<MovieSearchItem?>> = _listMovieSearch

    private val _listTvshowSearch = MutableLiveData<List<TvshowSearchItem?>>()
    val listTvshowSearch: LiveData<List<TvshowSearchItem?>> = _listTvshowSearch

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getTrendingMovies()
        getTrendingTvshow()
    }

    companion object {
        const val TAG = "SearchViewModel"
    }

    private fun getTrendingMovies() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getTrendingMovies(BuildConfig.API_KEY)
        client.enqueue(object : Callback<TrendingMoviesResponse> {
            override fun onResponse(
                call: Call<TrendingMoviesResponse>,
                response: Response<TrendingMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listTrendingMovies.postValue(responseBody.results!!)
                    }
                } else {
                    Log.d(TAG, "message : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TrendingMoviesResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
    }

    private fun getTrendingTvshow() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getTrendingTvshow(BuildConfig.API_KEY)
        client.enqueue(object : Callback<TrendingTvshowResponse> {
            override fun onResponse(
                call: Call<TrendingTvshowResponse>,
                response: Response<TrendingTvshowResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listTrendingTvshow.postValue(responseBody.results!!)
                    }
                } else {
                    Log.d(TAG, "message : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TrendingTvshowResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
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

    fun getMultiSearch(query: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().multiSearch(BuildConfig.API_KEY, query.toString().trim())
        client.enqueue(object : Callback<MultiSearchResponse> {
            override fun onResponse(
                call: Call<MultiSearchResponse>,
                response: Response<MultiSearchResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listSearch.postValue(responseBody.results!!)
                    } else {
                        Log.d(TAG, "Tidak ditemukan")
                    }
                } else {
                    Log.d(TAG, "message : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MultiSearchResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
    }
}