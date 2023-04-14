package com.bagas.project.filmbase.di

import com.bagas.project.filmbase.data.local.LocalDataSource
import com.bagas.project.filmbase.data.remote.ApiConfig
import com.bagas.project.filmbase.data.remote.ApiService
import com.bagas.project.filmbase.data.remote.RemoteDataSource
import com.bagas.project.filmbase.data.repository.LocalRepository
import com.bagas.project.filmbase.data.repository.LocalRepositoryImpl
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.data.repository.MovieRepositoryImpl
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
    fun provideRemoteRepository(remoteDataSource: RemoteDataSource): MovieRepository = MovieRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideLocalRepository(localDataSource: LocalDataSource): LocalRepository = LocalRepositoryImpl(localDataSource)
}