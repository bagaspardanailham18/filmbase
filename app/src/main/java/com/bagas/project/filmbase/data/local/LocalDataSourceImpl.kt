package com.bagas.project.filmbase.data.local

import androidx.lifecycle.LiveData
import com.bagas.project.filmbase.data.local.entities.FavoriteMovieEntity
import com.bagas.project.filmbase.data.local.entities.FavoriteTvEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSourceImpl @Inject constructor(
    private val movieRoomDatabase: MovieRoomDatabase
): LocalDataSource {
    override suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity) {
        //hapus penggunaan appExecutor
        movieRoomDatabase.movieDao().insertFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity) {
        movieRoomDatabase.movieDao().deleteFavoriteMovie(movie)
    }

    override fun getFavoriteMovies(): LiveData<List<FavoriteMovieEntity>> {
        return movieRoomDatabase.movieDao().getAllFavoriteMovies()
    }

    override suspend fun insertFavoriteTvshow(tv: FavoriteTvEntity) {
        movieRoomDatabase.tvshowDao().insertFavoriteTvshow(tv)
    }

    override suspend fun deleteFavoriteTvshow(tv: FavoriteTvEntity) {
        movieRoomDatabase.tvshowDao().deleteFavoriteTvshow(tv)
    }

    override fun getFavoriteTvshows(): LiveData<List<FavoriteTvEntity>> {
        return movieRoomDatabase.tvshowDao().getAllFavoriteTvshows()
    }

    override fun getFavoriteMovieById(id: Int): LiveData<FavoriteMovieEntity> {
        return movieRoomDatabase.movieDao().getFavoriteMovieById(id)
    }

    override fun getFavoriteTvById(id: Int): LiveData<FavoriteTvEntity> {
        return movieRoomDatabase.tvshowDao().getFavoriteTvById(id)
    }

    override fun getFavoriteMoviesByQuery(query: String): LiveData<List<FavoriteMovieEntity>> {
        return movieRoomDatabase.movieDao().getFavoriteMovieByQuery(query)
    }

    override fun getFavoriteTvByQuery(query: String): LiveData<List<FavoriteTvEntity>> {
        return movieRoomDatabase.tvshowDao().getFavoriteTvByQuery(query)
    }
}