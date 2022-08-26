package com.bagas.project.filmbase.ui.home.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.remote.ApiConfig
import com.bagas.project.filmbase.data.responses.TopRatedMoviesItem
import com.bagas.project.filmbase.data.responses.TopRatedMoviesResponse
import com.bagas.project.filmbase.data.responses.UpcomingMoviesItem
import com.bagas.project.filmbase.data.responses.UpcomingMoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private val _listUpcomingMovies = MutableLiveData<List<UpcomingMoviesItem?>>()
    val listUpcomingMovies: LiveData<List<UpcomingMoviesItem?>> = _listUpcomingMovies

    private val _listAllUpcomingMovies = MutableLiveData<List<UpcomingMoviesItem?>>()
    val listAllUpcomingMovies: LiveData<List<UpcomingMoviesItem?>> = _listAllUpcomingMovies

    private val _listTopRatedMovies = MutableLiveData<List<TopRatedMoviesItem>>()
    val listTopRatedMovies: LiveData<List<TopRatedMoviesItem>> = _listTopRatedMovies

    private val _totalPages = MutableLiveData<Int>()
    val totapPages: LiveData<Int> = _totalPages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUpcomingMovies()
        getTopRatedMovies()
    }

    companion object {
        const val TAG = "MovieFragment"
    }

    private fun getUpcomingMovies() {
        val client = ApiConfig.getApiService().getUpcomingMovies(BuildConfig.API_KEY,1)
        _isLoading.value = true
        client.enqueue(object : Callback<UpcomingMoviesResponse> {
            override fun onResponse(
                call: Call<UpcomingMoviesResponse>,
                response: Response<UpcomingMoviesResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                Log.d("Hasil", responseBody.toString())
                if (response.isSuccessful && responseBody != null) {
                    _listUpcomingMovies.postValue(responseBody.results)
                    _totalPages.postValue(responseBody.totalPages)
                } else {
                    Log.d(TAG, "message : ${response.message()}")
                }

            }

            override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "message : ${t.message}")
            }

        })
    }

    fun getAllUpcomingMovies(totalPages: Int) {
        _isLoading.value = true
        for (i in 1..totalPages) {
            val client = ApiConfig.getApiService().getUpcomingMovies(BuildConfig.API_KEY,i)
            client.enqueue(object : Callback<UpcomingMoviesResponse> {
                override fun onResponse(
                    call: Call<UpcomingMoviesResponse>,
                    response: Response<UpcomingMoviesResponse>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    Log.d("Hasil", responseBody.toString())
                    if (response.isSuccessful && responseBody != null) {
                        _listAllUpcomingMovies.postValue(responseBody.results)
                    } else {
                        Log.d(TAG, "message : ${response.message()}")
                    }

                }

                override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d(TAG, "message : ${t.message}")
                }

            })
        }
    }

    private fun getTopRatedMovies() {
        val client = ApiConfig.getApiService().getTopRatedMovies(BuildConfig.API_KEY)
        client.enqueue(object : Callback<TopRatedMoviesResponse> {
            override fun onResponse(
                call: Call<TopRatedMoviesResponse>,
                response: Response<TopRatedMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listTopRatedMovies.value = responseBody.results
                    }
                } else {
                    Log.d(TAG, "message : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TopRatedMoviesResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
    }

}
