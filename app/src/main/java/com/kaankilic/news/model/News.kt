package com.kaankilic.news.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class News(

    @ColumnInfo("author")
    val author: String? = null,
    @ColumnInfo("title")
    val title: String?,
    @ColumnInfo("description")
    val description: String?,
    @ColumnInfo("url")
    val url: String?,
    @ColumnInfo("urlToImage")
    val urlToImage: String?,
    @ColumnInfo("publishedAt")
    val publishedAt: String?,
    @ColumnInfo("content")
    val content: String? = null,
    @ColumnInfo("isFavourite")
    var isFavourite: Boolean = false


){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0

}
