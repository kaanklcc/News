package com.kaankilic.news.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaankilic.news.RoomDB.NewsDatabase
import com.kaankilic.news.Util.OzelSharedPreferences
import com.kaankilic.news.model.News
import com.kaankilic.news.model.NewsResponse
import com.kaankilic.news.service.NewsAPIServis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsListViewModel(application: Application) : AndroidViewModel(application) {
    val news = MutableLiveData<List<News>>()
    val newsError = MutableLiveData<Boolean>()
    val newsLoading = MutableLiveData<Boolean>()

    private val newsAPIServis = NewsAPIServis()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())
    val guncellemeZamani = 10*60*1000*1000L


    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAL()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime()-kaydedilmeZamani<guncellemeZamani){
            verilieriRoomdanAl()
        }else{
            verileriIntarnettenAl()

        }



    }
    fun refreshDataFromIntarnet(){
        verileriIntarnettenAl()
    }


    private fun verileriIntarnettenAl(){
        newsLoading.value=true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = newsAPIServis.getData("us","e2bb4316068b40cabad31d7be413b6c2")
                if(response.isSuccessful){
                    response.body()?.let { newsResponse ->
                        val newsList = newsResponse.articles.map {
                            News(

                                author = it.author,
                                title = it.title,
                                description = it.description,
                                url = it.url,
                                urlToImage = it.urlToImage,
                                publishedAt = it.publishedAt,
                                content = it.content

                            )
                        }
                        withContext(Dispatchers.Main){
                            roomaKaydet(newsList)
                            Toast.makeText(getApplication(), "Intarnet", Toast.LENGTH_SHORT).show()
                        }
                    }


                }else{
                    withContext(Dispatchers.Main){
                        newsLoading.value=false
                        newsError.value=true
                    }
                }

            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    newsLoading.value=false
                    newsError.value=true
                    e.printStackTrace()
                }
            }

        }
    }

    private fun verilieriRoomdanAl(){
        newsLoading.value=true
        viewModelScope.launch(Dispatchers.IO) {
            val newsListesi = NewsDatabase(getApplication()).newsDao().getAllNews()
            withContext(Dispatchers.Main){
                //besinleri göstericez
                NewsGoster(newsListesi)
                Toast.makeText(getApplication(), "Room", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun NewsGoster(newsListesi : List<News>){
        newsLoading.value=true
        newsError.value=false
        news.value = newsListesi

    }

    private fun roomaKaydet(newsListesi: List<News>) {
        viewModelScope.launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            dao.deleteAll()
            val uuidListesi = dao.insertAll(*newsListesi.toTypedArray())
            var i = 0
            while (i < newsListesi.size) {
                newsListesi[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            NewsGoster(newsListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }

}