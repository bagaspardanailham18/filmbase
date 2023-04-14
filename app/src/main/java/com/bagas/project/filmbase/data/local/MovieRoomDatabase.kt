package com.bagas.project.filmbase.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UpcomingMovieEntity::class, TopRatedMovieEntity::class, AiringTodayTvEntity::class, TopRatedTvEntity::class, TrendingMovieEntity::class, TrendingTvshowEntity::class, FavoriteMovieEntity::class, FavoriteTvEntity::class], version = 1)
abstract class MovieRoomDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvshowDao(): TvshowDao

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MovieRoomDatabase {
            if (INSTANCE == null) {
                synchronized(MovieRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, MovieRoomDatabase::class.java, "movie_database")
                        .build()
                }
            }

            return INSTANCE as MovieRoomDatabase
        }
    }

}