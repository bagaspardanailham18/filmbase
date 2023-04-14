package com.bagas.project.filmbase.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    // Add suspend function for kotlin coroutines support and replace executor uses

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUpcomingMovies(upcomingMovie: List<UpcomingMovieEntity>)

    @Query("DELETE FROM upcoming_movie_entities")
    suspend fun deleteAllUpcomingMovie()

    @Query("SELECT * FROM upcoming_movie_entities ORDER BY release_date ASC")
    fun getUpcomingMovies(): LiveData<List<UpcomingMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTopRatedMovies(topRatedMovie: List<TopRatedMovieEntity>)

    @Query("DELETE FROM top_rated_movie_entities")
    suspend fun deleteAllTopRatedMovie()

    @Query("SELECT * FROM top_rated_movie_entities ORDER BY vote_average DESC")
    fun getTopRatedMovies(): LiveData<List<TopRatedMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrendingMovie(trendingMovie: List<TrendingMovieEntity>)

    @Query("DELETE FROM trending_movie_entities")
    suspend fun deleteAllTrendingMovie()

    @Query("SELECT * FROM trending_movie_entities ORDER BY release_date ASC")
    fun getTrendingMovies(): LiveData<List<TrendingMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity)

    @Update
    suspend fun updateFavoriteMovie(movie: FavoriteMovieEntity)

    @Delete
    suspend fun deleteFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("SELECT * from favorite_movie_entities ORDER BY id ASC")
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovieEntity>>

    @Query("SELECT * from favorite_movie_entities WHERE id = :id")
    fun getFavoriteMovieById(id: Int): LiveData<FavoriteMovieEntity>

    @Query("SELECT * from favorite_movie_entities WHERE title LIKE :query")
    fun getFavoriteMovieByQuery(query: String): LiveData<List<FavoriteMovieEntity>>

}