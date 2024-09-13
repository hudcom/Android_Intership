package com.project.android_intership.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.android_intership.databinding.FragmentNewsFeedBinding
import com.project.android_intership.ui.adapter.NewsAdapter
import com.project.android_intership.ui.view.viewmodel.NewsFeedViewModel

class NewsFeedFragment : Fragment() {

    // Створюємо змінну для ViewBinding
    private lateinit var binding: FragmentNewsFeedBinding
    private lateinit var viewModel: NewsFeedViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }


    // Налаштування Recycler View
    private fun setupRecyclerView(){
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // TODO: Get data for Adapter
        adapter = NewsAdapter(emptyList())
        recyclerView.adapter = adapter
    }

    // Налаштування ViewModel
    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(NewsFeedViewModel::class.java)

        // Спостерігаємо за змінами в даних
        /*        viewModel.newsList.observe(viewLifecycleOwner) { newsItems ->
                    *//*adapter.updateData(newsItems)*//*
        }*/
    }

}