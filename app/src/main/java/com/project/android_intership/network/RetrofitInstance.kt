package com.project.android_intership.network

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Налаштовуємо Retrofit для підключення до Reddit API
class RetrofitInstance {
    companion object{
        private const val BASE_URL = "https://www.reddit.com/"
        private val client = OkHttpClient.Builder().build()

        fun getApi(): RedditAPI {
            Log.d("TEST","Start to get API...")
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditAPI::class.java)
        }
    }
}