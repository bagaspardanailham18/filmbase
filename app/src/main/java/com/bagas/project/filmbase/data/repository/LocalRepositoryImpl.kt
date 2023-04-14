package com.bagas.project.filmbase.data.repository

import androidx.lifecycle.LiveData
import com.bagas.project.filmbase.data.local.LocalDataSource
import com.bagas.project.filmbase.data.local.entities.FavoriteMovieEntity
import com.bagas.project.filmbase.data.local.entities.FavoriteTvEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): LocalRepository {

    override suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity) {
        //hapus penggunaan appExecutor
        localDataSource.insertFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity) {
        localDataSource.deleteFavoriteMovie(movie)
    }

    override fun getFavoriteMovies(): LiveData<List<FavoriteMovieEntity>> {
        return localDataSource.getFavoriteMovies()
    }

    override suspend fun insertFavoriteTvshow(tv: FavoriteTvEntity) {
        localDataSource.insertFavoriteTvshow(tv)
    }

    override suspend fun deleteFavoriteTvshow(tv: FavoriteTvEntity) {
        localDataSource.deleteFavoriteTvshow(tv)
    }

    override fun getFavoriteTvshows(): LiveData<List<FavoriteTvEntity>> {
        return localDataSource.getFavoriteTvshows()
    }

    override fun getFavoriteMovieById(id: Int): LiveData<FavoriteMovieEntity> {
        return localDataSource.getFavoriteMovieById(id)
    }

    override fun getFavoriteTvById(id: Int): LiveData<FavoriteTvEntity> {
        return localDataSource.getFavoriteTvById(id)
    }

    override fun getFavoriteMoviesByQuery(query: String): LiveData<List<FavoriteMovieEntity>> {
        return localDataSource.getFavoriteMoviesByQuery(query)
    }

    override fun getFavoriteTvByQuery(query: String): LiveData<List<FavoriteTvEntity>> {
        return localDataSource.getFavoriteTvByQuery(query)
    }

}