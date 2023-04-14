package com.bagas.project.filmbase.ui.home.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bagas.project.filmbase.DataDummy
import com.bagas.project.filmbase.MainDispatcherRule
import com.bagas.project.filmbase.data.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.AiringTodayTvEntity
import com.bagas.project.filmbase.data.local.TopRatedTvEntity
import com.bagas.project.filmbase.getOrAwaitValue
import kotlinx.coroutines.test.runTest
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
class TvshowViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var tvshowViewModel: TvshowViewModel

    @Before
    fun setUp() {
        tvshowViewModel = TvshowViewModel(movieRepository)
    }

    @Test
    fun `when Get Airing Today Tvshow Should Not Null`() = runTest {
        val expectedData = MutableLiveData<Result<List<AiringTodayTvEntity>>>()
        expectedData.value = Result.Success(DataDummy.generateAiringTodayTvshow())

        `when`(movieRepository.getAiringTodayTv()).thenReturn(expectedData)

        val actual = tvshowViewModel.getAiringTodayTvshow().getOrAwaitValue()

        verify(movieRepository).getAiringTodayTv()
        assertNotNull(actual)
        assertEquals(DataDummy.generateAiringTodayTvshow().size, (actual as Result.Success).data.size)
    }

    @Test
    fun `when Get Top Rated Tvshow Should Not Null`() = runTest {
        val expectedData = MutableLiveData<Result<List<TopRatedTvEntity>>>()
        expectedData.value = Result.Success(DataDummy.generateTopRatedTvshow())

        `when`(movieRepository.getTopRatedTvshow()).thenReturn(expectedData)

        val actual = tvshowViewModel.getTopRatedTvshow().getOrAwaitValue()

        verify(movieRepository).getTopRatedTvshow()
        assertNotNull(actual)
        assertEquals(DataDummy.generateTopRatedTvshow().size, (actual as Result.Success).data.size)
    }

}