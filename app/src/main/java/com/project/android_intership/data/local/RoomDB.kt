package com.project.android_intership.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.android_intership.data.model.PostData

@Database(entities = [PostData::class], version = 1)
abstract class RoomDB: RoomDatabase() {
    abstract fun postDao(): PostDao
}