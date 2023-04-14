package com.bagas.project.filmbase.data.local

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie_entities")
data class FavoriteMovieEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "overview")
    val overview: String? = null,

    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = 0.0,

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null

)
