package com.bagas.project.filmbase.ui.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bagas.project.filmbase.DataDummy
import com.bagas.project.filmbase.MainDispatcherRule
import com.bagas.project.filmbase.data.local.UpcomingMovieEntity
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.AiringTodayTvEntity
import com.bagas.project.filmbase.data.local.TopRatedMovieEntity
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
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var movieViewModel: MovieViewModel

    @Before
    fun setUp() {
        movieViewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun `when Get Upcoming Movies Should Not Null and Data Size Same As Expected`() = runTest {
        val expectedData = MutableLiveData<Result<List<UpcomingMovieEntity>>>()
        expectedData.value = Result.Success(DataDummy.generateUpcomingMovieResponse())

        `when`(movieRepository.getUpcomingMovies()).thenReturn(expectedData)

        val actual = movieViewModel.getUpcomingMovies().getOrAwaitValue()

        verify(movieRepository).getUpcomingMovies()
        assertNotNull(actual)
        assertEquals(DataDummy.generateUpcomingMovieResponse().size, (actual as Result.Success).data.size)
    }

    @Test
    fun `when Get Top Rated Movies Should Not Null and Data Size Same As Expected`() {
        val expectedData = MutableLiveData<Result<List<TopRatedMovieEntity>>>()
        expectedData.value = Result.Success(DataDummy.generateTopRatedMovieResponse())

        `when`(movieRepository.getTopRatedMovies()).thenReturn(expectedData)

        val actual = movieViewModel.getTopRatedMovies().getOrAwaitValue()

        verify(movieRepository).getTopRatedMovies()
        assertNotNull(actual)
        assertEquals(DataDummy.generateTopRatedMovieResponse().size, (actual as Result.Success).data.size)
    }

}