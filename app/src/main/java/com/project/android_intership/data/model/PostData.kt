package com.project.android_intership.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostData(
    @PrimaryKey val id: String,
    val title:String,
    val selftext:String,
    val author_fullname:String,
    val created: Long,
    val num_comments: Int,
    val thumbnail: String?,
    val url: String?
)