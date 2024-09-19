package com.project.android_intership.ui.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.project.android_intership.MyApplication
import com.project.android_intership.data.local.RoomDB
import com.project.android_intership.data.model.PostData
import com.project.android_intership.data.repository.RedditPagingSource
import com.project.android_intership.network.RetrofitInstance
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {
    private val redditAPI = RetrofitInstance.getApi()
    private val database: RoomDB = (application as MyApplication).database
    private val _topPosts = MutableLiveData<PagingData<PostData>>()
    val topPosts: LiveData<PagingData<PostData>>
        get() = _topPosts

    fun loadNews() {
        Log.d("TEST","Start get top posts...")
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 5,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { RedditPagingSource(redditAPI, database.postDao()) }
            ).flow
                .cachedIn(viewModelScope) // Кешування постів у viewModelScope
                .collectLatest { _topPosts.postValue(it) } // Відправляємо дані у LiveData
        }
    }

    fun loadTopPostsFromCache(){
        Log.d("TEST","Get Posts From Cache")
        viewModelScope.launch {
            // Отримання постів з кешу
            val cachedPosts = database.postDao().getTopPosts()
            if (cachedPosts.isNotEmpty()) {
                _topPosts.postValue(PagingData.from(cachedPosts))
            }
        }
    }
}