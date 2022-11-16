package com.bagas.project.filmbase.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bagas.project.filmbase.DataDummy
import com.bagas.project.filmbase.MainDispatcherRule
import com.bagas.project.filmbase.data.local.TrendingMovieEntity
import com.bagas.project.filmbase.data.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.TrendingTvshowEntity
import com.bagas.project.filmbase.getOrAwaitValue
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
class SearchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        searchViewModel = SearchViewModel(movieRepository)
    }

    @Test
    fun `when Get Trending Movies Should Not Null And Return Success`() = runTest {
        val dummyTrendingMovies = DataDummy.generateTrendingMoviesResponse()
        val expectedData = MutableLiveData<Result<List<TrendingMovieEntity>>>()
        expectedData.value = Result.Success(dummyTrendingMovies)

        `when`(movieRepository.getTrendingMovies()).thenReturn(expectedData)
        val actual = searchViewModel.getTrendingMovies().getOrAwaitValue()

        verify(movieRepository).getTrendingMovies()
        assertNotNull(actual)
        assertTrue(actual is Result.Success)
        assertEquals(dummyTrendingMovies.size, (actual as Result.Success).data.size)
        assertEquals(dummyTrendingMovies[1].title, actual.data[1].title)
    }

    @Test
    fun `when Get Trending Tv Should Not Null And Return Success`() = runTest {
        val dummyTrendingTv = DataDummy.generateTrendingTvResponse()
        val expectedData = MutableLiveData<Result<List<TrendingTvshowEntity>>>()
        expectedData.value = Result.Success(dummyTrendingTv)

        `when`(movieRepository.getTrendingTvshows()).thenReturn(expectedData)
        val actual = searchViewModel.getTrendingTvshow().getOrAwaitValue()

        verify(movieRepository).getTrendingTvshows()
        assertNotNull(actual)
        assertTrue(actual is Result.Success)
        assertEquals(dummyTrendingTv.size, (actual as Result.Success).data.size)
        assertEquals(dummyTrendingTv[1].name, actual.data[1].name)
    }

}