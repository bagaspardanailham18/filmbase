package com.bagas.project.filmbase.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TvshowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAiringToday(airingTodayTv: List<AiringTodayTvEntity>)

    @Query("DELETE FROM airing_today_tv_entities")
    suspend fun deleteAllAiringToday()

    @Query("SELECT * FROM airing_today_tv_entities ORDER BY first_air_date ASC")
    fun getAiringTodayTv(): LiveData<List<AiringTodayTvEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTopRatedTv(topRatedTv: List<TopRatedTvEntity>)

    @Query("DELETE FROM top_rated_tv_entities")
    suspend fun deleteAllTopRatedTv()

    @Query("SELECT * FROM top_rated_tv_entities ORDER BY vote_average DESC")
    fun getTopRatedTv(): LiveData<List<TopRatedTvEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrendingTvshow(trendingTvshow: List<TrendingTvshowEntity>)

    @Query("DELETE FROM trending_tv_entities")
    suspend fun deleteAllTrendingTvshow()

    @Query("SELECT * FROM trending_tv_entities ORDER BY first_air_date ASC")
    fun getTrendingTvshows(): LiveData<List<TrendingTvshowEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteTvshow(tv: FavoriteTvEntity)

    @Update
    suspend fun updateFavoriteTvshow(tv: FavoriteTvEntity)

    @Delete
    suspend fun deleteFavoriteTvshow(tv: FavoriteTvEntity)

    @Query("SELECT * from favorite_tv_entities ORDER BY id ASC")
    fun getAllFavoriteTvshows(): LiveData<List<FavoriteTvEntity>>

    @Query("SELECT * from favorite_tv_entities WHERE id = :id")
    fun getFavoriteTvById(id: Int): LiveData<FavoriteTvEntity>

    @Query("SELECT * from favorite_tv_entities WHERE name LIKE :query")
    fun getFavoriteTvByQuery(query: String): LiveData<List<FavoriteTvEntity>>
}