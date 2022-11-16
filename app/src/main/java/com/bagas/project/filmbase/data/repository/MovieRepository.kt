package com.bagas.project.filmbase.data.repository

import android.util.Log
import androidx.lifecycle.*
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.*
import com.bagas.project.filmbase.data.remote.ApiService
import com.bagas.project.filmbase.data.responses.*
import com.bagas.project.filmbase.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val apiService: ApiService,
    private val movieRoomDatabase: MovieRoomDatabase
) {

    fun getUpcomingMovies(): LiveData<Result<List<UpcomingMovieEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUpcomingMovies(BuildConfig.API_KEY, 1)
            val upcomingMovies = response.results
            val upcomingMovieList = upcomingMovies.map { data ->
                UpcomingMovieEntity(
                    data!!.id,
                    data.title,
                    data.overview,
                    data.releaseDate,
                    data.voteAverage,
                    data.posterPath,
                    data.backdropPath
                )
            }
            movieRoomDatabase.movieDao().deleteAllUpcomingMovie()
            movieRoomDatabase.movieDao().insertUpcomingMovies(upcomingMovieList)
        } catch (e: Exception) {
            Log.d("MovieRepository", "getUpcomingMovies : ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<UpcomingMovieEntity>>> = movieRoomDatabase.movieDao().getUpcomingMovies().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getTopRatedMovies(): LiveData<Result<List<TopRatedMovieEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getTopRatedMovies(BuildConfig.API_KEY)
            val topRatedMovies = response.results
            val topRatedMovieList = topRatedMovies.map { data ->
                TopRatedMovieEntity(
                    data.id,
                    data.title,
                    data.overview,
                    data.releaseDate,
                    data.voteAverage,
                    data.posterPath,
                    data.backdropPath
                )
            }
            movieRoomDatabase.movieDao().deleteAllTopRatedMovie()
            movieRoomDatabase.movieDao().insertTopRatedMovies(topRatedMovieList)
        } catch (e: Exception) {
            Log.d("MovieRepository", "getTopRatedMovies : ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<TopRatedMovieEntity>>> = movieRoomDatabase.movieDao().getTopRatedMovies().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getAiringTodayTv(): LiveData<Result<List<AiringTodayTvEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAiringTodayTvshow(BuildConfig.API_KEY)
            val airingTodayTvshows = response.results
            val airingTodayTvList = airingTodayTvshows?.map { data ->
                AiringTodayTvEntity(
                    data?.id,
                    data?.name,
                    data?.overview,
                    data?.firstAirDate,
                    data?.voteAverage,
                    data?.posterPath,
                    data?.backdropPath
                )
            }
            movieRoomDatabase.tvshowDao().deleteAllAiringToday()
            movieRoomDatabase.tvshowDao().insertAiringToday(airingTodayTvList!!)
        } catch (e: Exception) {
            Log.d("MovieRepository", "getAiringTodayTv : ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<AiringTodayTvEntity>>> = movieRoomDatabase.tvshowDao().getAiringTodayTv().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getTopRatedTvshow(): LiveData<Result<List<TopRatedTvEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getTopRatedTvshow(BuildConfig.API_KEY)
            val topRatedTvshows = response.results
            val topRatedTvList = topRatedTvshows?.map { data ->
                TopRatedTvEntity(
                    data?.id,
                    data?.name,
                    data?.overview,
                    data?.firstAirDate,
                    data?.voteAverage,
                    data?.posterPath,
                    data?.backdropPath
                )
            }
            movieRoomDatabase.tvshowDao().deleteAllTopRatedTv()
            movieRoomDatabase.tvshowDao().insertTopRatedTv(topRatedTvList!!)
        } catch (e: Exception) {
            Log.d("MovieRepository", "getTopRatedTvshows : ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<TopRatedTvEntity>>> = movieRoomDatabase.tvshowDao().getTopRatedTv().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getTrendingMovies(): LiveData<Result<List<TrendingMovieEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getTrendingMovies(BuildConfig.API_KEY)
            val trendingMovies = response.results
            val trendingMovieList = trendingMovies?.map { data ->
                TrendingMovieEntity(
                    data?.id!!,
                    data.title,
                    data.overview,
                    data.releaseDate,
                    data.voteAverage,
                    data.posterPath,
                    data.backdropPath
                )
            }
            movieRoomDatabase.movieDao().deleteAllTrendingMovie()
            movieRoomDatabase.movieDao().insertTrendingMovie(trendingMovieList!!)
        } catch (e: Exception) {
            Log.d("MovieRepository", "getTrendingMovies : ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<TrendingMovieEntity>>> = movieRoomDatabase.movieDao().getTrendingMovies().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getTrendingTvshows(): LiveData<Result<List<TrendingTvshowEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getTrendingTvshow(BuildConfig.API_KEY)
            val trendingTvshows = response.results
            val trendingTvList = trendingTvshows?.map { data ->
                TrendingTvshowEntity(
                    data?.id!!,
                    data.name,
                    data.overview,
                    data.firstAirDate,
                    data.voteAverage,
                    data.posterPath,
                    data.backdropPath
                )
            }
            movieRoomDatabase.tvshowDao().deleteAllTrendingTvshow()
            movieRoomDatabase.tvshowDao().insertTrendingTvshow(trendingTvList!!)
        } catch (e: Exception) {
            Log.d("MovieRepository", "getTrendingTv : ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<TrendingTvshowEntity>>> = movieRoomDatabase.tvshowDao().getTrendingTvshows().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity) {
        //hapus penggunaan appExecutor
        movieRoomDatabase.movieDao().insertFavoriteMovie(movie)
    }

    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity) {
        movieRoomDatabase.movieDao().deleteFavoriteMovie(movie)
    }

    fun getFavoriteMovies(): LiveData<List<FavoriteMovieEntity>> {
        return movieRoomDatabase.movieDao().getAllFavoriteMovies()
    }

    suspend fun insertFavoriteTvshow(tv: FavoriteTvEntity) {
        movieRoomDatabase.tvshowDao().insertFavoriteTvshow(tv)
    }

    suspend fun deleteFavoriteTvshow(tv: FavoriteTvEntity) {
        movieRoomDatabase.tvshowDao().deleteFavoriteTvshow(tv)
    }

    fun getFavoriteTvshows(): LiveData<List<FavoriteTvEntity>> {
        return movieRoomDatabase.tvshowDao().getAllFavoriteTvshows()
    }

    fun getFavoriteMovieById(id: Int): LiveData<FavoriteMovieEntity> {
        return movieRoomDatabase.movieDao().getFavoriteMovieById(id)
    }

    fun getFavoriteTvById(id: Int): LiveData<FavoriteTvEntity> {
        return movieRoomDatabase.tvshowDao().getFavoriteTvById(id)
    }

    fun getFavoriteMoviesByQuery(query: String): LiveData<List<FavoriteMovieEntity>> {
        return movieRoomDatabase.movieDao().getFavoriteMovieByQuery(query)
    }

    fun getFavoriteTvByQuery(query: String): LiveData<List<FavoriteTvEntity>> {
        return movieRoomDatabase.tvshowDao().getFavoriteTvByQuery(query)
    }

}