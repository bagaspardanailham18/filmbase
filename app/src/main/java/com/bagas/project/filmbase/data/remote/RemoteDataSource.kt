package com.bagas.project.filmbase.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.entities.*
import com.bagas.project.filmbase.data.responses.MovieDetailResponse
import com.bagas.project.filmbase.data.responses.MovieVideoResponse
import com.bagas.project.filmbase.data.responses.TvshowDetailResponse
import com.bagas.project.filmbase.data.responses.TvshowVideoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

interface RemoteDataSource {

    fun getUpcomingMovies(): Flow<Result<List<UpcomingMovieEntity>>>

    fun getTopRatedMovies(): Flow<Result<List<TopRatedMovieEntity>>>
    suspend fun getMovieDetail(movieId: Int?): Flow<Result<MovieDetailResponse>>
    suspend fun getTvshowDetail(tvshowId: Int?): Flow<Result<TvshowDetailResponse>>

    suspend fun getMovieVideos(movieId: Int?): Flow<Result<MovieVideoResponse>>

    suspend fun getTvshowVideos(tvshowId: Int?): Flow<Result<TvshowVideoResponse>>

    fun getAiringTodayTv(): Flow<Result<List<AiringTodayTvEntity>>>

    fun getTopRatedTvshow(): Flow<Result<List<TopRatedTvEntity>>>
    fun getTrendingMovies(): Flow<Result<List<TrendingMovieEntity>>>
    fun getTrendingTvshows(): Flow<Result<List<TrendingTvshowEntity>>>
}