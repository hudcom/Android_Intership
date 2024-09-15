package com.project.android_intership.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.android_intership.databinding.FragmentNewsFeedBinding
import com.project.android_intership.ui.adapter.NewsAdapter
import com.project.android_intership.ui.view.viewmodel.NewsFeedViewModel

class NewsFeedFragment : Fragment() {

    // Створюємо змінну для ViewBinding
    private lateinit var binding: FragmentNewsFeedBinding
    private lateinit var viewModel: NewsFeedViewModel
    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()

        initLiveDataListeners()

        Log.d("TEST", "Get post list")
        getPostsList()
    }


    // Налаштування Recycler View
    private fun setupRecyclerView(){
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = NewsAdapter()
        recyclerView.adapter = adapter
    }

    private fun initLiveDataListeners(){
        Log.d("TEST", "init listeners for liveData")
        viewModel.topPosts.observe(viewLifecycleOwner){ posts ->
            if (posts.isNotEmpty()) {
                adapter.updatePosts(posts)
            } else {
                Log.d("TEST", "No posts to update.")
            }
        }
    }

    private fun getPostsList(){
        // Тепер запит на отримання постів
        viewModel.getTopPosts(10)
    }

    // Налаштування ViewModel
    private fun setupViewModel(){
        viewModel = ViewModelProvider(this)[NewsFeedViewModel::class.java]
    }

}