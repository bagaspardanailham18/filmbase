package com.bagas.project.filmbase.data.repository

import androidx.lifecycle.*
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.*
import com.bagas.project.filmbase.data.local.entities.*
import com.bagas.project.filmbase.data.remote.RemoteDataSource
import com.bagas.project.filmbase.data.responses.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {

    override fun getUpcomingMovies(): Flow<Result<List<UpcomingMovieEntity>>> {
        return remoteDataSource.getUpcomingMovies()
    }

    override fun getTopRatedMovies(): Flow<Result<List<TopRatedMovieEntity>>> {
        return remoteDataSource.getTopRatedMovies()
    }

    override suspend fun getMovieDetail(movieId: Int?): Flow<Result<MovieDetailResponse>> {
        return remoteDataSource.getMovieDetail(movieId)
    }

    override suspend fun getTvshowDetail(tvshowId: Int?): Flow<Result<TvshowDetailResponse>> {
        return remoteDataSource.getTvshowDetail(tvshowId)
    }

    override suspend fun getMovieVideos(movieId: Int?): Flow<Result<MovieVideoResponse>> {
        return remoteDataSource.getMovieVideos(movieId)
    }

    override suspend fun getTvshowVideos(tvshowId: Int?): Flow<Result<TvshowVideoResponse>> {
        return remoteDataSource.getTvshowVideos(tvshowId)
    }

    override fun getAiringTodayTv(): Flow<Result<List<AiringTodayTvEntity>>> {
        return remoteDataSource.getAiringTodayTv()
    }

    override fun getTopRatedTvshow(): Flow<Result<List<TopRatedTvEntity>>> {
        return remoteDataSource.getTopRatedTvshow()
    }

    override fun getTrendingMovies(): Flow<Result<List<TrendingMovieEntity>>> {
        return remoteDataSource.getTrendingMovies()
    }

    override fun getTrendingTvshows(): Flow<Result<List<TrendingTvshowEntity>>> {
        return remoteDataSource.getTrendingTvshows()
    }

}