package com.bagas.project.filmbase.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bagas.project.filmbase.DataDummy
import com.bagas.project.filmbase.MainDispatcherRule
import com.bagas.project.filmbase.data.local.FavoriteMovieEntity
import com.bagas.project.filmbase.data.local.FavoriteTvEntity
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var favoriteViewModel: FavoriteViewModel

    @Before
    fun setUp() {
        favoriteViewModel = FavoriteViewModel(movieRepository)
    }

    @Test
    fun `when Get Favorite Tvshows Should Not Null And Return Success`() = runTest {
        var expectedTvhows = MutableLiveData<List<FavoriteTvEntity>>()
        expectedTvhows.value = DataDummy.generateFavoriteTvEntity()

        `when`(movieRepository.getFavoriteTvshows()).thenReturn(expectedTvhows)
        val actual = favoriteViewModel.getFavoriteTvshows().getOrAwaitValue()

        verify(movieRepository).getFavoriteTvshows()
        assertNotNull(actual)
        assertEquals(expectedTvhows.value, actual)
        assertEquals(expectedTvhows.value!!.size, actual.size)
        assertEquals(expectedTvhows.value!![1].name, actual[1].name)
    }

    @Test
    fun `when Get Favorite Movies Should Not Null And Return Success`() = runTest {
        var expectedMovies = MutableLiveData<List<FavoriteMovieEntity>>()
        expectedMovies.value = DataDummy.generateFavoriteMovieEntity()

        `when`(movieRepository.getFavoriteMovies()).thenReturn(expectedMovies)
        val actual = favoriteViewModel.getFavoriteMovies().getOrAwaitValue()

        verify(movieRepository).getFavoriteMovies()
        assertNotNull(actual)
        assertEquals(expectedMovies.value, actual)
        assertEquals(expectedMovies.value!!.size, actual.size)
        assertEquals(expectedMovies.value!![1].title, actual[1].title)
    }

    @Test
    fun `when Get Favorite Tvshows By Query Should Not Null And Return Success`() = runTest {
        var expectedTvhows = MutableLiveData<List<FavoriteTvEntity>>()
        expectedTvhows.value = DataDummy.generateFavoriteTvEntity()

        `when`(movieRepository.getFavoriteTvByQuery("query")).thenReturn(expectedTvhows)
        val actual = favoriteViewModel.getFavoriteTvByQuery("query").getOrAwaitValue()

        verify(movieRepository).getFavoriteTvByQuery("query")
        assertNotNull(actual)
        assertEquals(expectedTvhows.value, actual)
        assertEquals(expectedTvhows.value!!.size, actual.size)
        assertEquals(expectedTvhows.value!![1].name, actual[1].name)
    }

    @Test
    fun `when Get Favorite Movies By Query Should Not Null And Return Success`() = runTest {
        var expectedMovies = MutableLiveData<List<FavoriteMovieEntity>>()
        expectedMovies.value = DataDummy.generateFavoriteMovieEntity()

        `when`(movieRepository.getFavoriteMoviesByQuery("query")).thenReturn(expectedMovies)
        val actual = favoriteViewModel.getFavoriteMoviesByQuery("query").getOrAwaitValue()

        verify(movieRepository).getFavoriteMoviesByQuery("query")
        assertNotNull(actual)
        assertEquals(expectedMovies.value, actual)
        assertEquals(expectedMovies.value!!.size, actual.size)
        assertEquals(expectedMovies.value!![1].title, actual[1].title)
    }

}