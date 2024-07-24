package com.kaankilic.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kaankilic.news.Util.gorselIndir
import com.kaankilic.news.Util.placeHolderYap
import com.kaankilic.news.databinding.NewsRecyclerRowBinding
import com.kaankilic.news.model.News

import com.kaankilic.news.view.NewsSearchFragmentDirections


class SourceAdapter(val newsListesi : ArrayList<News>) : RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {

    class SourceViewHolder(val binding: NewsRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val binding =
            NewsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SourceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsListesi.size
    }

    fun updateNewsListesi(newNewsList: List<News>) {
        newsListesi.clear()
        newsListesi.addAll(newNewsList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val news = newsListesi[position]
        with(holder.binding) {
            baslik.text = news.title ?: ""
            altHaber.text = news.description ?: ""
            year.text = news.publishedAt ?: ""
            holder.itemView.setOnClickListener {
                val action =
                    NewsSearchFragmentDirections.actionNewsSearchFragmentToNewsDetailFragment(id = newsListesi[position].uuid)
                Navigation.findNavController(it).navigate(action)
            }
            val imageUrl = newsListesi[position].urlToImage
            val placeHolder = placeHolderYap(holder.itemView.context)
            holder.binding.imageView.gorselIndir(imageUrl, placeHolder)
        }
    }
}