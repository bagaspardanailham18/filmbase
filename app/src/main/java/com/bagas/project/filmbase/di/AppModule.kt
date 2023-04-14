package com.bagas.project.filmbase.di

import com.bagas.project.filmbase.data.local.MovieRoomDatabase
import com.bagas.project.filmbase.data.remote.ApiConfig
import com.bagas.project.filmbase.data.remote.ApiService
import com.bagas.project.filmbase.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiConfig.getApiService()

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, movieRoomDatabase: MovieRoomDatabase): MovieRepository = MovieRepository(apiService, movieRoomDatabase)

}