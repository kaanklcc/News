package com.kaankilic.news.service

import com.kaankilic.news.model.NewsResponse
import org.jsoup.Jsoup
import retrofit2.Response

class NewsAPIServis {
    private val api = RetrofitInstance.api

    suspend fun getData(Country : String , apiKey : String):Response<NewsResponse>{
        return  api.getTopHeadlines(Country,apiKey)
    }



}