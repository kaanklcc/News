package com.kaankilic.news.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaankilic.news.adapter.NewsAdapter
import com.kaankilic.news.databinding.FragmentNewsListBinding
import com.kaankilic.news.viewModel.NewsListViewModel


class NewsListFragment : Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : NewsListViewModel
    private val newsRecyclerAdapter = NewsAdapter(arrayListOf())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsListViewModel::class.java]
        viewModel.refreshData()
        binding.newsRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.newsRecyclerView.adapter= newsRecyclerAdapter


        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.newsHataMesaj.visibility=View.GONE
            binding.progressBar.visibility=View.VISIBLE
            binding.newsRecyclerView.visibility=View.GONE
            viewModel.refreshDataFromIntarnet()
            binding.swipeRefreshLayout.isRefreshing=false

        }
        observeLiveData()
    }



    private fun observeLiveData(){
        viewModel.news.observe(viewLifecycleOwner){
            //recyclerviewgelcek
            newsRecyclerAdapter.updateNewsListesi(it)
            binding.newsRecyclerView.visibility=View.VISIBLE
        }
        viewModel.newsLoading.observe(viewLifecycleOwner){
            if (it){
                binding.newsHataMesaj.visibility=View.GONE
                binding.progressBar.visibility=View.GONE
                binding.newsRecyclerView.visibility=View.VISIBLE

            }else{
                binding.progressBar.visibility=View.GONE
            }

        }
        viewModel.newsError.observe(viewLifecycleOwner){
            if (it){
                binding.progressBar.visibility=View.GONE
                binding.newsHataMesaj.visibility=View.VISIBLE
                binding.newsRecyclerView.visibility=View.GONE
            }else{
                binding.newsHataMesaj.visibility=View.GONE
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}