package com.bagas.project.filmbase.data.local.entities

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "airing_today_tv_entities")
data class AiringTodayTvEntity(

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
