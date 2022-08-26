package com.bagas.project.filmbase.data.remote

import com.bagas.project.filmbase.data.responses.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : Call<UpcomingMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ) : Call<TopRatedMoviesResponse>

    @GET("tv/airing_today")
    fun getAiringTodayTvshow(
        @Query("api_key") apiKey: String
    ) : Call<AiringTodayTvshowResponse>

    @GET("tv/top_rated")
    fun getTopRatedTvshow(
        @Query("api_key") apiKey: String
    ) : Call<TopRatedTvshowResponse>

    @GET("trending/movie/day")
    fun getTrendingMovies(
        @Query("api_key") apiKey: String
    ) : Call<TrendingMoviesResponse>

    @GET("trending/tv/day")
    fun getTrendingTvshow(
        @Query("api_key") apiKey: String
    ) : Call<TrendingTvshowResponse>

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
    fun multiSearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ) : Call<MultiSearchResponse>

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