package com.bagas.project.filmbase.ui.favorite

import androidx.lifecycle.ViewModel
import com.bagas.project.filmbase.data.repository.LocalRepository
import com.bagas.project.filmbase.data.repository.MovieRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val localRepository: LocalRepository): ViewModel() {

    fun getFavoriteTvshows() = localRepository.getFavoriteTvshows()
    fun getFavoriteMovies() = localRepository.getFavoriteMovies()

    fun getFavoriteMoviesByQuery(query: String) = localRepository.getFavoriteMoviesByQuery(query)
    fun getFavoriteTvByQuery(query: String) = localRepository.getFavoriteTvByQuery(query)

}