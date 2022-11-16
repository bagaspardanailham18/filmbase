package com.bagas.project.filmbase

import com.bagas.project.filmbase.data.local.*
import com.bagas.project.filmbase.data.responses.*

object DataDummy {

    fun generateUpcomingMovieResponse(): List<UpcomingMovieEntity> {
        val items: MutableList<UpcomingMovieEntity> = arrayListOf()
        for (i in 0..10) {
            val data = UpcomingMovieEntity(
                i,
                "title $i",
                "overview $i",
                "releaseDate $i",
                i.toDouble(),
                "posterPath $i",
                "backdropPath $i"
            )
            items.add(data)
        }
        return items
    }

    fun generateTopRatedMovieResponse(): List<TopRatedMovieEntity> {
        val items: MutableList<TopRatedMovieEntity> = arrayListOf()
        for (i in 0..10) {
            val data = TopRatedMovieEntity(
                i,
                "title $i",
                "overview $i",
                "releaseDate $i",
                i.toDouble(),
                "posterPath $i",
                "backdropPath $i"
            )
            items.add(data)
        }
        return items
    }

    fun generateTrendingMoviesResponse(): List<TrendingMovieEntity> {
        val items: MutableList<TrendingMovieEntity> = arrayListOf()
        for (i in 0..10) {
            val data = TrendingMovieEntity(
                i,
                "title $i",
                "overview $i",
                "releaseDate $i",
                i.toDouble(),
                "posterPath $i",
                "backdropPath $i"
            )
            items.add(data)
        }
        return items
    }

    fun generateAiringTodayTvshow(): List<AiringTodayTvEntity> {
        val items: MutableList<AiringTodayTvEntity> = arrayListOf()
        for (i in 0..10) {
            val data = AiringTodayTvEntity(
                i,
                "name $i",
                "overview $i",
                "firstAirDate $i",
                i.toDouble(),
                "posterPath $i",
                "backdropPath $i"
            )
            items.add(data)
        }
        return items
    }

    fun generateTopRatedTvshow(): List<TopRatedTvEntity> {
        val items: MutableList<TopRatedTvEntity> = arrayListOf()
        for (i in 0..10) {
            val data = TopRatedTvEntity(
                i,
                "title $i",
                "overview $i",
                "releaseDate $i",
                i.toDouble(),
                "posterPath $i",
                "backdropPath $i"
            )
            items.add(data)
        }
        return items
    }

    fun generateTrendingTvResponse(): List<TrendingTvshowEntity> {
        val items: MutableList<TrendingTvshowEntity> = arrayListOf()
        for (i in 0..10) {
            val data = TrendingTvshowEntity(
                i,
                "name $i",
                "overview $i",
                "firstAirDate $i",
                i.toDouble(),
                "posterPath $i",
                "backdropPath $i"
            )
            items.add(data)
        }
        return items
    }

    fun generateFavoriteTvEntity(): List<FavoriteTvEntity> {
        val items: MutableList<FavoriteTvEntity> = arrayListOf()
        for (i in 1..10) {
            val data = FavoriteTvEntity(
                i,
                "name $i",
                "overview $i",
                "firstAirDate $i",
                i.toDouble(),
                "posterPath $i",
                "backdropPath $i"
            )
            items.add(data)
        }
        return items
    }

    fun generateFavoriteMovieEntity(): List<FavoriteMovieEntity> {
        val items: MutableList<FavoriteMovieEntity> = arrayListOf()
        for (i in 1..10) {
            val data = FavoriteMovieEntity(
                i,
                "name $i",
                "overview $i",
                "releaseDate $i",
                i.toDouble(),
                "posterPath $i",
                "backdropPath $i"
            )
            items.add(data)
        }
        return items
    }

    fun generateMovieDetailResponse(): MovieDetailResponse {
        return MovieDetailResponse(
            "EN",
            "imdbId",
            true,
            "title",
            "backdropPath",
            listOf(MovieGenresItem("name", 1)),
            0.0,
            listOf(MovieProductionCountriesItem("idn", "Indonesia")),
            1,
            10,
            "overview",
            "originalTitle",
            0,
            "posterPath",
            listOf(MovieSpokenLanguagesItem("English", "en")),
            listOf(MovieProductionCompaniesItem("logo", "name", 1, "country")),
            "releaseDate",
            0.0,
            false
        )
    }

    fun generateFavoriteMovieByIdEntity(): FavoriteMovieEntity {
        return FavoriteMovieEntity(
                1,
                "title",
                "overview",
                "releaseDate",
                0.0,
                "posterPath",
                "backdropPath"
            )
    }

    fun generateFavoriteTvByIdEntity(): FavoriteTvEntity {
        return FavoriteTvEntity(
            1,
            "name",
            "overview",
            "firstAirDate",
            0.0,
            "posterPath",
            "backdropPath"
        )
    }

}