package com.kaankilic.news.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.news.RoomDB.NewsDatabase
import com.kaankilic.news.model.News
import kotlinx.coroutines.launch

class NewsFavouriteViewModel(application: Application) : AndroidViewModel(application) {
    val newsMutable = MutableLiveData<List<News>>()

    fun roomVerisiniAl(uuid : Int){
        viewModelScope.launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            val news = dao.getAllNews()
            newsMutable.postValue(news)
        }
    }

    fun getFavouriteNews() {
        viewModelScope.launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            val favouriteNewsList = dao.getFavouriteNews()
            newsMutable.postValue(favouriteNewsList)
        }
    }

}