package com.project.android_intership

import android.app.Application
import androidx.room.Room
import com.project.android_intership.data.local.RoomDB

class MyApplication: Application() {
    lateinit var database: RoomDB

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            RoomDB::class.java,
            "app_database"
        ).build()
    }
}