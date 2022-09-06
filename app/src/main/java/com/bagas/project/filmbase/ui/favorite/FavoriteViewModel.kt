package com.bagas.project.filmbase.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.data.repository.MovieRepository

class FavoriteViewModel(private val movieRepository: MovieRepository) : ViewModel() {

//    private val mMovieRepository: MovieRepository = MovieRepository(application)
//
//    fun getAllFavoritedMovies(): LiveData<List<MovieEntity>> = mMovieRepository.getAllFavoriteMovies()


    fun getFavoriteTvshows() = movieRepository.getFavoriteTvshows()
    fun getFavoriteMovies() = movieRepository.getFavoriteMovies()

    fun getFavoriteMoviesByQuery(query: String) = movieRepository.getFavoriteMoviesByQuery(query)
    fun getFavoriteTvByQuery(query: String) = movieRepository.getFavoriteTvByQuery(query)
}