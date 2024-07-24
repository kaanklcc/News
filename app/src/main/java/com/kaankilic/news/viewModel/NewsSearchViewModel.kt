package com.kaankilic.news.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.news.RoomDB.NewsDatabase
import com.kaankilic.news.model.News
import kotlinx.coroutines.launch

class NewsSearchViewModel(application: Application) : AndroidViewModel(application) {
    val newsAll = MutableLiveData<List<News>>()

    private fun NewsGoster(newsListesi : List<News>){

        newsAll.value = newsListesi

    }
    fun searchNews(query : String){
        viewModelScope.launch {
            val newsList = NewsDatabase(getApplication()).newsDao().getAllNews()
            val filteredList = newsList.filter { news ->
                news.title?.contains(query,ignoreCase = true)?:false
            }
            NewsGoster(filteredList)

        }
    }
}