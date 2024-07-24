package com.kaankilic.news.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaankilic.news.model.News

@Dao
interface NewsDao {
    @Insert
    suspend fun insertAll(vararg news : News): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews (news : News)

    @Query("SELECT * FROM news WHERE uuid = :newsId")
    suspend fun getNews(newsId : Int) : News

    @Query("SELECT * FROM news")
    suspend fun getAllNews(): List<News>

    @Query("DELETE FROM news")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNews(news : News)

    @Query("SELECT * FROM news WHERE isFavourite = 1")
    suspend fun getFavouriteNews(): List<News>


}