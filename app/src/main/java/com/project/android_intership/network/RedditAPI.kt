package com.project.android_intership.network

import com.project.android_intership.data.model.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditAPI {
    @GET("/top.json")
    suspend fun getTopPosts(@Query("limit") limit: Int): RedditResponse
}