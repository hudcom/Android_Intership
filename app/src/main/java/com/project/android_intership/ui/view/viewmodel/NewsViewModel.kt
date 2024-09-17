package com.project.android_intership.ui.view.viewmodel

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore.Images.Media
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.android_intership.utils.ImageDataCallback

class NewsViewModel : ViewModel(), ImageDataCallback {
    private var _bitmap:Bitmap? = null
    private var _format: String? = null

    // LiveData для зберігання URL зображення, яке буде відкрите
    val imageUrl = MutableLiveData<String>()

    fun onImageClicked(url: String) {
        imageUrl.value = url
    }

    fun saveImage(context: Context){
        if (_bitmap != null && _format != null){
            saveImageToGallery(_bitmap!!, _format!!,context)
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap, format: String, context: Context){
        // Це об'єкт, який містить набір пар "ключ-значення". У цьому випадку він зберігає метадані про зображення
        val contentValues = createContentValues(format)

        // Отримуємо доступ до ContentResolver, через який працюємо з медіа-контентом на пристрої.
        val resolver = context.contentResolver

        // Викликаємо метод insert, щоб додати новий запис у медіабібліотеку з нашими даними (ContentValues).
        val uri = resolver.insert(Media.EXTERNAL_CONTENT_URI, contentValues)

        // Якщо uri не є null, це означає, що запис успішно створено.
        uri?.let {
            // Відкриваємо потік для запису зображення у відповідне місце.
            resolver.openOutputStream(it).use { outputStream ->
                // Записуємо Bitmap у цей потік, конвертуючи його в JPEG формат з якістю 100%.
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            }
        }
    }

    private fun createContentValues(format:String): ContentValues =
        ContentValues().apply {
            put(Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.${format}")
            put(Media.MIME_TYPE,"image/$format")
            put(Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

    override fun sendImageData(bitmap: Bitmap, format: String) {
        _bitmap = bitmap
        _format = format
    }
}