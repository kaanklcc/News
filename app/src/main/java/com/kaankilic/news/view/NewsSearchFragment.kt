package com.kaankilic.news.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaankilic.news.adapter.NewsAdapter
import com.kaankilic.news.adapter.SourceAdapter
import com.kaankilic.news.databinding.FragmentNewsSearchBinding
import com.kaankilic.news.viewModel.NewsSearchViewModel


class NewsSearchFragment : Fragment() {
    private var _binding: FragmentNewsSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : NewsSearchViewModel
    private val sourceRecyclerAdapter = SourceAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsSearchViewModel::class.java]
        binding.searchRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.searchRecyclerView.adapter=sourceRecyclerAdapter


        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let { viewModel.searchNews(it) }
                return true
            }
        })
        observeLiveData()

    }
    private fun observeLiveData(){
        viewModel.newsAll.observe(viewLifecycleOwner){
            sourceRecyclerAdapter.updateNewsListesi(it)
            binding.searchRecyclerView.visibility=View.VISIBLE


        }

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}