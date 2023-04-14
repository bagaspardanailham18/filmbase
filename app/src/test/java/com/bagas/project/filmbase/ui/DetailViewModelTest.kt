package com.bagas.project.filmbase.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.DataDummy
import com.bagas.project.filmbase.MainDispatcherRule
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.data.responses.MovieDetailResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.FavoriteMovieEntity
import com.bagas.project.filmbase.data.local.FavoriteTvEntity
import com.bagas.project.filmbase.data.remote.ApiService
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
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun `when Get Movie Detail Should Not Null`() = runTest {
        val actual = detailViewModel.getMovieDetail(1)
        assertNotNull(actual)
    }

    @Test
    fun `when Get Tv Detail Should Not Null`() = runTest {
        val actual = detailViewModel.getTvshowDetail(1)
        assertNotNull(actual)
    }

    @Test
    fun `when Get Movie Videos Should Not Null`() {
        val actual = detailViewModel.getMovieVideos(1)
        assertNotNull(actual)
    }

    @Test
    fun `when Get Tv Videos Should Not Null`() {
        val actual = detailViewModel.getTvVideos(1)
        assertNotNull(actual)
    }

    @Test
    fun `when Get Favorited Movie By Id`() = runTest {
        val dummyData = DataDummy.generateFavoriteMovieByIdEntity()
        val expectedData = MutableLiveData<FavoriteMovieEntity>()
        expectedData.value = dummyData

        `when`(movieRepository.getFavoriteMovieById(1)).thenReturn(expectedData)
        val actual = detailViewModel.getFavoritedMovieById(1)

        verify(movieRepository).getFavoriteMovieById(1)
        assertNotNull(actual)
        assertEquals(dummyData.title, actual.value?.title)
    }

    @Test
    fun `when Get Favorited Tv By Id`() = runTest {
        val dummyData = DataDummy.generateFavoriteTvByIdEntity()
        val expectedData = MutableLiveData<FavoriteTvEntity>()
        expectedData.value = dummyData

        `when`(movieRepository.getFavoriteTvById(1)).thenReturn(expectedData)
        val actual = detailViewModel.getFavoritedTvById(1)

        verify(movieRepository).getFavoriteTvById(1)
        assertNotNull(actual)
        assertEquals(dummyData.name, actual.value?.name)
    }

    @Test
    fun `insert Favorited Movie`() = runTest {
        val dummyData = DataDummy.generateFavoriteMovieByIdEntity()
        `when`(movieRepository.insertFavoriteMovie(dummyData))

        val actual = detailViewModel.insertFavoritedMovie(dummyData)
        verify(movieRepository).insertFavoriteMovie(dummyData)
        assertNotNull(actual)
    }

    @Test
    fun `insert Favorited Tv`() = runTest {
        val dummyData = DataDummy.generateFavoriteTvByIdEntity()
        `when`(movieRepository.insertFavoriteTvshow(dummyData))

        val actual = detailViewModel.insertFavoritedTv(dummyData)
        verify(movieRepository).insertFavoriteTvshow(dummyData)
        assertNotNull(actual)
    }

    @Test
    fun `delete Favorited Movie`() = runTest {
        val dummyData = DataDummy.generateFavoriteMovieByIdEntity()
        `when`(movieRepository.deleteFavoriteMovie(dummyData))

        val actual = detailViewModel.deleteFavoritedMovie(dummyData)
        verify(movieRepository).deleteFavoriteMovie(dummyData)
        assertNotNull(actual)
    }

    @Test
    fun `delete Favorited Tv`() = runTest {
        val dummyData = DataDummy.generateFavoriteTvByIdEntity()
        `when`(movieRepository.deleteFavoriteTvshow(dummyData))

        val actual = detailViewModel.deleteFavoritedTv(dummyData)
        verify(movieRepository).deleteFavoriteTvshow(dummyData)
        assertNotNull(actual)
    }

}