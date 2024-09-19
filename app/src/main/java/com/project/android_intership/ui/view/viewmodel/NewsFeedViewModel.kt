package com.project.android_intership.ui.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.project.android_intership.data.model.PostData
import com.project.android_intership.data.repository.RedditPagingSource
import com.project.android_intership.network.RetrofitInstance
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsFeedViewModel : ViewModel() {
    private val redditAPI = RetrofitInstance.getApi()
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
                pagingSourceFactory = { RedditPagingSource(redditAPI) }
            ).flow
                .cachedIn(viewModelScope) // Кешування постів у viewModelScope
                .collectLatest { _topPosts.postValue(it) } // Відправляємо дані у LiveData
        }
    }
}