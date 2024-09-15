package com.project.android_intership.ui.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.android_intership.data.model.PostData
import com.project.android_intership.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsFeedViewModel : ViewModel() {
    private val redditAPI = RetrofitInstance.getApi()

    private val _topPosts = MutableLiveData<List<PostData>>()
    val topPosts: LiveData<List<PostData>>
        get() = _topPosts

    fun getTopPosts(limit: Int) {
        Log.d("TEST","Start get top posts...")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = redditAPI.getTopPosts(limit)
                _topPosts.postValue(response.data.children.map {
                    it.data
                })
                Log.d("TEST","SUCCESS get top post.")
            } catch (e: Exception) {
                _topPosts.postValue(emptyList())
                Log.d("TEST","ERROR get top post.")
            }
        }
    }
}