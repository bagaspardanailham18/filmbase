package com.bagas.project.filmbase.di

import android.content.Context
import com.bagas.project.filmbase.data.local.LocalDataSource
import com.bagas.project.filmbase.data.local.LocalDataSourceImpl
import com.bagas.project.filmbase.data.local.MovieRoomDatabase
import com.bagas.project.filmbase.data.remote.ApiService
import com.bagas.project.filmbase.data.remote.RemoteDataSource
import com.bagas.project.filmbase.data.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieRoomDatabase = MovieRoomDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideLocalDataSource(movieRoomDatabase: MovieRoomDatabase): LocalDataSource = LocalDataSourceImpl(movieRoomDatabase)

    @Provides
    @Singleton
    fun remoteDataSource(movieRoomDatabase: MovieRoomDatabase, apiService: ApiService): RemoteDataSource =
        RemoteDataSourceImpl(movieRoomDatabase, apiService)
}