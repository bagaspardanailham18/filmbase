package com.bagas.project.filmbase.data.local

import androidx.lifecycle.LiveData
import com.bagas.project.filmbase.data.local.entities.FavoriteMovieEntity
import com.bagas.project.filmbase.data.local.entities.FavoriteTvEntity

interface LocalDataSource {
    suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity)

    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity)

    fun getFavoriteMovies(): LiveData<List<FavoriteMovieEntity>>

    suspend fun insertFavoriteTvshow(tv: FavoriteTvEntity)

    suspend fun deleteFavoriteTvshow(tv: FavoriteTvEntity)

    fun getFavoriteTvshows(): LiveData<List<FavoriteTvEntity>>

    fun getFavoriteMovieById(id: Int): LiveData<FavoriteMovieEntity>

    fun getFavoriteTvById(id: Int): LiveData<FavoriteTvEntity>

    fun getFavoriteMoviesByQuery(query: String): LiveData<List<FavoriteMovieEntity>>

    fun getFavoriteTvByQuery(query: String): LiveData<List<FavoriteTvEntity>>
}