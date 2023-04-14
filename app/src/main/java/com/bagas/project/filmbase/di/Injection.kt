package com.bagas.project.filmbase.di

import android.content.Context
import com.bagas.project.filmbase.data.local.MovieRoomDatabase
import com.bagas.project.filmbase.data.remote.ApiConfig
import com.bagas.project.filmbase.data.repository.MovieRepository
import com.bagas.project.filmbase.utils.AppExecutors

object Injection {

//    fun provideRepository(context: Context): MovieRepository {
//        val apiService = ApiConfig.getApiService()
//        val database = MovieRoomDatabase.getDatabase(context)
//        val movieDao = database.movieDao()
//        val tvshowDao = database.tvshowDao()
//        val appExecutors = AppExecutors()
//        return MovieRepository.getInstance(apiService, movieDao, tvshowDao, appExecutors)
//    }

}