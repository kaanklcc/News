package com.kaankilic.news.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.kaankilic.news.Util.gorselIndir
import com.kaankilic.news.Util.placeHolderYap
import com.kaankilic.news.databinding.FragmentNewsDetailBinding
import com.kaankilic.news.model.News
import com.kaankilic.news.viewModel.NewsDetailViewModel
import com.kaankilic.news.viewModel.NewsListViewModel


class NewsDetailFragment : Fragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : NewsDetailViewModel
    var newsId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favouriteButton.setOnClickListener { favourite(it) }


        viewModel = ViewModelProvider(this)[NewsDetailViewModel::class.java]
        arguments?.let {
            newsId= NewsDetailFragmentArgs.fromBundle(it).id

        }
        viewModel.roomVerisiniAl(newsId)
        observeLiveData()
    }
    fun favourite(view: View){
        viewModel.newsLiveData.value?.let {


            val news = News(
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                isFavourite = true

            )

            viewModel.favouriteNews(news)
            val action = NewsDetailFragmentDirections.actionNewsDetailFragmentToNewsFavouriteFragment(ID = newsId)
            Navigation.findNavController(view).navigate(action)
        }

    }


    fun observeLiveData() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            binding.yL.text = it.publishedAt ?: ""
            binding.baslik.text = it.title ?: ""
            binding.altBaslik.text = it.description ?: ""
            binding.haberDetay.text=it.content ?: ""
            binding.url.text=it.url ?: ""
            val imageUrl = it.urlToImage
            imageUrl?.let { url ->
                val placeHolder = placeHolderYap(requireContext())
                binding.imageView2.gorselIndir(url, placeHolder)

            }

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}