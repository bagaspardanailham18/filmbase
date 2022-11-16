package com.bagas.project.filmbase.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavoriteTvshows() = movieRepository.getFavoriteTvshows()
    fun getFavoriteMovies() = movieRepository.getFavoriteMovies()

    fun getFavoriteMoviesByQuery(query: String) = movieRepository.getFavoriteMoviesByQuery(query)
    fun getFavoriteTvByQuery(query: String) = movieRepository.getFavoriteTvByQuery(query)

}