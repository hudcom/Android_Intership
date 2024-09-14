package com.project.android_intership.data.model

data class PostData(
    val title:String,
    val selftext:String,
    val author_fullname:String,
    val created: Long,
    val num_comments: Int,
    val thumbnail: String?,
    val url: String?
)