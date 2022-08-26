package com.bagas.project.filmbase.ui.home.tvshow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.remote.ApiConfig
import com.bagas.project.filmbase.data.responses.AiringTodayTvshowItem
import com.bagas.project.filmbase.data.responses.AiringTodayTvshowResponse
import com.bagas.project.filmbase.data.responses.TopRatedTvshowItem
import com.bagas.project.filmbase.data.responses.TopRatedTvshowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvshowViewModel: ViewModel() {

    private val _listAiringTodayTvshow = MutableLiveData<List<AiringTodayTvshowItem?>>()
    val listAiringTodayTvshow: LiveData<List<AiringTodayTvshowItem?>> = _listAiringTodayTvshow

    private val _listTopRatedTvshow = MutableLiveData<List<TopRatedTvshowItem?>>()
    val listTopRatedTvshow: LiveData<List<TopRatedTvshowItem?>> = _listTopRatedTvshow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        const val TAG = "TvshowViewModel"
    }

    init {
        getAiringTodayTvshow()
        getTopRatedTvshow()
    }

    private fun getAiringTodayTvshow() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAiringTodayTvshow(BuildConfig.API_KEY)
        client.enqueue(object : Callback<AiringTodayTvshowResponse> {
            override fun onResponse(
                call: Call<AiringTodayTvshowResponse>,
                response: Response<AiringTodayTvshowResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listAiringTodayTvshow.postValue(responseBody.results!!)
                    }
                } else {
                    Log.d(TAG, "message : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AiringTodayTvshowResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
    }

    private fun getTopRatedTvshow() {
        val client = ApiConfig.getApiService().getTopRatedTvshow(BuildConfig.API_KEY)
        client.enqueue(object : Callback<TopRatedTvshowResponse> {
            override fun onResponse(
                call: Call<TopRatedTvshowResponse>,
                response: Response<TopRatedTvshowResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listTopRatedTvshow.postValue(responseBody.results!!)
                    }
                } else {
                    Log.d(TAG, "message : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TopRatedTvshowResponse>, t: Throwable) {
                Log.d(TAG, "message : ${t.message}")
            }

        })
    }

}