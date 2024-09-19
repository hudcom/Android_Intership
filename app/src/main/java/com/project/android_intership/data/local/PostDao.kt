package com.project.android_intership.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.android_intership.data.model.PostData

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopPosts(posts: List<PostData>)

    @Query("SELECT * FROM posts")
    suspend fun getTopPosts(): List<PostData>

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts()
}