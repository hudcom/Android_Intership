package com.project.android_intership.ui.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {
    // LiveData для зберігання URL зображення, яке буде відкрите
    val imageUrl = MutableLiveData<String>()

    fun onImageClicked(url: String) {
        imageUrl.value = url
    }
}