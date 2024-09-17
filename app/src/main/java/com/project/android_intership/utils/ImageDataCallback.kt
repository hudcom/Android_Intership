package com.project.android_intership.utils

import android.graphics.Bitmap

interface ImageDataCallback {
    fun sendImageData(bitmap: Bitmap, format: String)
}