package com.kaankilic.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kaankilic.news.databinding.NewsRecyclerRowBinding
import com.kaankilic.news.model.News
import com.kaankilic.news.Util.gorselIndir
import com.kaankilic.news.Util.placeHolderYap
import com.kaankilic.news.view.NewsListFragmentDirections

class NewsAdapter(val newsListesi : ArrayList<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding : NewsRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsListesi.size
    }

    fun updateNewsListesi(newNewsList : List<News>){
        newsListesi.clear()
        newsListesi.addAll(newNewsList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsListesi[position]
        holder.binding.baslik.text = news.title ?: ""
        holder.binding.altHaber.text = news.description ?: ""
        holder.binding.year.text = news.publishedAt ?: ""
        holder.itemView.setOnClickListener {
            val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(id = newsListesi[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        val imageUrl = newsListesi[position].urlToImage
        val placeHolder= placeHolderYap(holder.itemView.context)
        holder.binding.imageView.gorselIndir(imageUrl,placeHolder)
    }
}