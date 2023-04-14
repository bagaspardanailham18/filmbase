package com.bagas.project.filmbase.data.local.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_rated_tv_entities")
data class TopRatedTvEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "overview")
    val overview: String? = null,

    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String? = null,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = 0.0,

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null

)
