package com.kaankilic.news.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.news.RoomDB.NewsDatabase
import com.kaankilic.news.model.News
import com.kaankilic.news.service.NewsAPIServis
import kotlinx.coroutines.launch

class NewsDetailViewModel(application: Application) : AndroidViewModel(application) {
    val newsLiveData = MutableLiveData<News>()
    val favouriteNewsLiveData = MutableLiveData<List<News>>()

    fun roomVerisiniAl(uuid : Int){
        viewModelScope.launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            val news = dao.getNews(uuid)
            newsLiveData.value=news

        }
    }

    fun favouriteNews(news:News){
        viewModelScope.launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            dao.insertNews(news)
           /* val favouriteNewsList = dao.getAllNews()
            favouriteNewsLiveData.postValue(favouriteNewsList)*/
            updateFavouriteNews()


        }
    }
    private fun updateFavouriteNews() {
        viewModelScope.launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            val favouriteNewsList = dao.getFavouriteNews()
            // FavouriteLiveData'ya güncel veriyi gönder
            favouriteNewsLiveData.postValue(favouriteNewsList)
        }


    }
}