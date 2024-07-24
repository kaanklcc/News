package com.kaankilic.news.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
  @SerializedName("articles")
    val articles: List<Article>
)
