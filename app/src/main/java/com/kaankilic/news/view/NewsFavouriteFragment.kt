package com.kaankilic.news.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaankilic.news.adapter.FavouriteAdapter
import com.kaankilic.news.databinding.FragmentNewsFavouriteBinding
import com.kaankilic.news.viewModel.NewsFavouriteViewModel


class NewsFavouriteFragment : Fragment() {
    private var _binding: FragmentNewsFavouriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : NewsFavouriteViewModel
    private val favouriteRecyclerAdapter = FavouriteAdapter(arrayListOf())
    var newsId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsFavouriteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favouriteRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.favouriteRecyclerView.adapter=favouriteRecyclerAdapter
        viewModel = ViewModelProvider(this)[NewsFavouriteViewModel::class.java]

        arguments?.let {
            newsId = NewsFavouriteFragmentArgs.fromBundle(it).ID
        }
        viewModel.getFavouriteNews()

        observeLiveData()

    }
    override fun onResume() {
        super.onResume()
        // Fragment her yeniden göründüğünde favori haberleri güncelle
        viewModel.getFavouriteNews()
    }

    fun observeLiveData(){
        viewModel.newsMutable.observe(viewLifecycleOwner){ newsList ->
            newsList?.let {
                favouriteRecyclerAdapter.updateNewsListesi(it)
                binding.favouriteRecyclerView.visibility=View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}