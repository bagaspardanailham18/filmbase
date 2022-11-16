package com.bagas.project.filmbase.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.UpcomingMovieEntity
import com.bagas.project.filmbase.data.responses.UpcomingMoviesItem

class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService).apply {
                    instance = this
                }
            }
    }

//    fun getUpcomingMovies(): LiveData<Result<List<UpcomingMovieEntity>>> = liveData {
//        emit(Result.Loading)
//        try {
//            val response = apiService.getUpcomingMovies(BuildConfig.API_KEY, 1)
//            val upcomingMovies = response.results
//            val upcomingMovieList = upcomingMovies.map { data ->
//                UpcomingMovieEntity(
//                    data!!.id,
//                    data.title,
//                    data.overview,
//                    data.releaseDate,
//                    data.voteAverage,
//                    data.posterPath,
//                    data.backdropPath
//                )
//            }
//            movieDao.deleteAllUpcomingMovie()
//            movieDao.insertUpcomingMovies(upcomingMovieList)
//        } catch (e: Exception) {
//            Log.d("MovieRepository", "getUpcomingMovies : ${e.message.toString()}")
//            emit(Result.Error(e.message.toString()))
//        }
//        val localData: LiveData<Result<List<UpcomingMovieEntity>>> = movieDao.getUpcomingMovies().map {
//            Result.Success(it)
//        }
//        emitSource(localData)
//    }

}