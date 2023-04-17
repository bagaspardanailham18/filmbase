package com.bagas.project.filmbase.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.MovieRoomDatabase
import com.bagas.project.filmbase.data.local.entities.*
import com.bagas.project.filmbase.data.responses.MovieDetailResponse
import com.bagas.project.filmbase.data.responses.MovieVideoResponse
import com.bagas.project.filmbase.data.responses.TvshowDetailResponse
import com.bagas.project.filmbase.data.responses.TvshowVideoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val movieRoomDatabase: MovieRoomDatabase,
    private val apiService: ApiService
): RemoteDataSource {
    override fun getUpcomingMovies(): Flow<Result<List<UpcomingMovieEntity>>> = flow {
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
        val localData: Flow<Result<List<UpcomingMovieEntity>>> = movieRoomDatabase.movieDao().getUpcomingMovies().map {
            Result.Success(it)
        }
        emitAll(localData)
    }.flowOn(Dispatchers.IO)

    override fun getTopRatedMovies(): Flow<Result<List<TopRatedMovieEntity>>> = flow {
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
        val localData: Flow<Result<List<TopRatedMovieEntity>>> = movieRoomDatabase.movieDao().getTopRatedMovies().map {
            Result.Success(it)
        }
        emitAll(localData)
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetail(movieId: Int?): Flow<Result<MovieDetailResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getMovieDetail(movieId, BuildConfig.API_KEY)
            emit(Result.Success(response))
        } catch (t: Throwable) {
            if (t is HttpException) {
                when (t.code()) {
                    404 -> {
                        Result.Error("Not Found")
                    }
                }
            } else {
                Result.Error("No internet connection")
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTvshowDetail(tvshowId: Int?): Flow<Result<TvshowDetailResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getTvDetail(tvshowId, BuildConfig.API_KEY)
            emit(Result.Success(response))
        } catch (t: Throwable) {
            if (t is HttpException) {
                when (t.code()) {
                    404 -> {
                        Result.Error("Not Found")
                    }
                }
            } else {
                Result.Error("No internet connection")
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieVideos(movieId: Int?): Flow<Result<MovieVideoResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getMovieVideos(movieId, BuildConfig.API_KEY)
            emit(Result.Success(response))
        } catch (t: Throwable) {
            if (t is HttpException) {
                when (t.code()) {
                    404 -> {
                        Result.Error("Not Found")
                    }
                }
            } else {
                Result.Error("No internet connection")
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTvshowVideos(tvshowId: Int?): Flow<Result<TvshowVideoResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getTvVideos(tvshowId, BuildConfig.API_KEY)
            emit(Result.Success(response))
        } catch (t: Throwable) {
            if (t is HttpException) {
                when (t.code()) {
                    404 -> {
                        Result.Error("Not Found")
                    }
                }
            } else {
                Result.Error("No internet connection")
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getAiringTodayTv(): Flow<Result<List<AiringTodayTvEntity>>> = flow {
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
        val localData: Flow<Result<List<AiringTodayTvEntity>>> = movieRoomDatabase.tvshowDao().getAiringTodayTv().map {
            Result.Success(it)
        }
        emitAll(localData)
    }.flowOn(Dispatchers.IO)

    override fun getTopRatedTvshow(): Flow<Result<List<TopRatedTvEntity>>> = flow {
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
        val localData: Flow<Result<List<TopRatedTvEntity>>> = movieRoomDatabase.tvshowDao().getTopRatedTv().map {
            Result.Success(it)
        }
        emitAll(localData)
    }.flowOn(Dispatchers.IO)

    override fun getTrendingMovies(): Flow<Result<List<TrendingMovieEntity>>> = flow {
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
        val localData: Flow<Result<List<TrendingMovieEntity>>> = movieRoomDatabase.movieDao().getTrendingMovies().map {
            Result.Success(it)
        }
        emitAll(localData)
    }.flowOn(Dispatchers.IO)

    override fun getTrendingTvshows(): Flow<Result<List<TrendingTvshowEntity>>> = flow {
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
        val localData: Flow<Result<List<TrendingTvshowEntity>>> = movieRoomDatabase.tvshowDao().getTrendingTvshows().map {
            Result.Success(it)
        }
        emitAll(localData)
    }.flowOn(Dispatchers.IO)
}