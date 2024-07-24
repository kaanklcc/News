package com.kaankilic.news.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kaankilic.news.model.Source

data class Article(
    @SerializedName("source")
    val source: Source?,
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("content")
    val content: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0

}
