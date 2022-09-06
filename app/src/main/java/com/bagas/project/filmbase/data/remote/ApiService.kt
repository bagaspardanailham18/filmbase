package com.bagas.project.filmbase.data.remote

import com.bagas.project.filmbase.data.responses.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Add suspend and remove call<> for kotlin coroutines support

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : UpcomingMoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ) : TopRatedMoviesResponse

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvshow(
        @Query("api_key") apiKey: String
    ) : AiringTodayTvshowResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTvshow(
        @Query("api_key") apiKey: String
    ) : TopRatedTvshowResponse

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String
    ) : TrendingMoviesResponse

    @GET("trending/tv/day")
    suspend fun getTrendingTvshow(
        @Query("api_key") apiKey: String
    ) : TrendingTvshowResponse

    @GET("search/movie")
    fun getMovieSearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ) : Call<MovieSearchResponse>

    @GET("search/tv")
    fun getTvshowSearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ) : Call<TvshowSearchResponse>

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ) : MultiSearchResponse

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ) : Call<MovieDetailResponse>

    @GET("tv/{tv_id}")
    fun getTvDetail(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ) : Call<TvshowDetailResponse>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ) : Call<MovieVideoResponse>

    @GET("tv/{tv_id}/videos")
    fun getTvVideos(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ) : Call<TvshowVideoResponse>
}