package com.bagas.project.filmbase.di

import android.content.Context
import com.bagas.project.filmbase.data.local.MovieRoomDatabase
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

}