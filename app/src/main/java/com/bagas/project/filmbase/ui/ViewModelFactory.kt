package com.bagas.project.filmbase.ui

import androidx.lifecycle.ViewModelProvider
import com.bagas.project.filmbase.data.repository.MovieRepositoryImpl

class ViewModelFactory private constructor(private val movieRepositoryImpl: MovieRepositoryImpl): ViewModelProvider.NewInstanceFactory() {

//    companion object {
//        @Volatile
//        private var instance: ViewModelFactory? = null
//        fun getInstance(context: Context): ViewModelFactory =
//            instance ?: synchronized(this) {
//                instance ?: ViewModelFactory(Injection.provideRepository(context))
//            }.also { instance = it }
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
//            return MovieViewModel(movieRepository) as T
//        } else if (modelClass.isAssignableFrom(TvshowViewModel::class.java)) {
//            return TvshowViewModel(movieRepository) as T
//        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
//            return SearchViewModel(movieRepository) as T
//        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
//            return DetailViewModel(movieRepository) as T
//        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
//            return FavoriteViewModel(movieRepository) as T
//        }
//
//        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//    }

}