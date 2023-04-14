package com.bagas.project.filmbase.ui.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.data.repository.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

//    init {
//        getUpcomingMovies()
//        getTopRatedMovies()
//    }

    companion object {
        const val TAG = "MovieFragment"
    }

    fun getUpcomingMovies() = movieRepository.getUpcomingMovies()
    fun getTopRatedMovies() = movieRepository.getTopRatedMovies()

//    fun getAllUpcomingMovies(totalPages: Int) {
//        _isLoading.value = true
//        for (i in 1..totalPages) {
//            val client = ApiConfig.getApiService().getUpcomingMovies(BuildConfig.API_KEY,i)
//            client.enqueue(object : Callback<UpcomingMoviesResponse> {
//                override fun onResponse(
//                    call: Call<UpcomingMoviesResponse>,
//                    response: Response<UpcomingMoviesResponse>
//                ) {
//                    _isLoading.value = false
//                    val responseBody = response.body()
//                    Log.d("Hasil", responseBody.toString())
//                    if (response.isSuccessful && responseBody != null) {
//                        _listAllUpcomingMovies.postValue(responseBody.results)
//                    } else {
//                        Log.d(TAG, "message : ${response.message()}")
//                    }
//
//                }
//
//                override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
//                    _isLoading.value = false
//                    Log.d(TAG, "message : ${t.message}")
//                }
//
//            })
//        }
//    }

}
