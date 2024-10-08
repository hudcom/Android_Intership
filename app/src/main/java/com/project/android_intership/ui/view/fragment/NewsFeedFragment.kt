package com.project.android_intership.ui.view.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.android_intership.databinding.FragmentNewsBinding
import com.project.android_intership.databinding.FragmentNewsFeedBinding
import com.project.android_intership.ui.adapter.NewsAdapter
import com.project.android_intership.ui.view.viewmodel.NewsFeedViewModel
import com.project.android_intership.ui.view.viewmodel.NewsViewModel

class NewsFeedFragment : Fragment() {

    // Створюємо змінну для ViewBinding
    private lateinit var binding: FragmentNewsFeedBinding
    private lateinit var newsBinding: FragmentNewsBinding
    private lateinit var viewModel: NewsFeedViewModel
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        newsBinding = FragmentNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        initPostLiveDataListener()
        initUrlLiveDataListener()

        checkInternetConnection(savedInstanceState)
    }

    private fun checkInternetConnection(savedInstanceState: Bundle?) {
        if (!isOnline(requireContext())){
            Log.d("TEST","No internet connection")
            Toast.makeText(requireContext(),"Немає інтернет з'єднання", Toast.LENGTH_LONG).show()
            viewModel.loadTopPostsFromCache()
        } else {
            checkSavedInstanceState(savedInstanceState)
        }
    }

    // Перевірка інтернет з'єднання
    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }


    // Перевірка якщо фрагмент має вже збережений стан то завантажується він, якщо ні - завантажуються новини з API
    private fun checkSavedInstanceState(savedInstanceState: Bundle?) {
        if(savedInstanceState == null){
            viewModel.loadNews()
        } else{
            Log.d("TEST","Rotate screen: ${viewModel.topPosts.value}")
            viewModel.topPosts.value?.let { adapter.submitData(lifecycle,it) }
        }
    }


    // Налаштування Recycler View
    private fun setupRecyclerView(){
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setContextMenuForImageView()
        recyclerView.adapter = adapter
    }

    private fun setContextMenuForImageView(){
        adapter = NewsAdapter(newsViewModel) { view ->
            // Відкриваємо контекстне меню для конкретного елемента
            registerForContextMenu(view)
            view.showContextMenu()
        }
    }

    private fun initPostLiveDataListener(){
        viewModel.topPosts.observe(viewLifecycleOwner){ posts ->
            if (posts != null) {
                adapter.submitData(lifecycle, posts)
            } else {
                Log.d("TEST", "No posts to update.")
            }
        }
    }

    // Спостерігаємо за змінами в imageUrl
    private fun initUrlLiveDataListener(){
        newsViewModel.imageUrl.observe(viewLifecycleOwner) { url ->
            url?.let {
                // Відкриваємо Uri в новому вікні
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                newsViewModel.imageUrl.value = null
            // Видаляємо imageUrl щоб воно повторно не відкривалося при перестворенні Activity
            }
        }
    }

    // Налаштування ViewModel
    private fun setupViewModel(){
        viewModel = ViewModelProvider(this)[NewsFeedViewModel::class.java]
        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(0, v.id , 0, "Зберегти зображення")
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.title) {
            "Зберегти зображення" -> {
                Toast.makeText(requireActivity(),"Зображення завантажується", Toast.LENGTH_LONG).show()
                newsViewModel.saveImage(requireContext())
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}