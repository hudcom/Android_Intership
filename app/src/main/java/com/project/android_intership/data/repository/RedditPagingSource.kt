package com.project.android_intership.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.project.android_intership.data.model.PostData
import com.project.android_intership.network.RedditAPI

class RedditPagingSource(private val redditAPI: RedditAPI): PagingSource<String,PostData>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostData> {
        // Початкове значення позиції
        val position = params.key

        return try {
            // Робимо запит до redditAPI і витягуємо звідти limit елементів, починаючи від after.
            // "t3_$position" - спеціальний синтаксис який вимагає RedditAPI
            val response = redditAPI.getTopPosts(limit = params.loadSize, after = position)
            // Отримуємо пости
            val posts = response.data.children.map {
                it.data
            }
            // Оновлюємо ключ наступної сторінки
            val nextKey = response.data.after
            // Завантажуємо сторінку
            LoadResult.Page(
                data = posts,
                prevKey = null, // Підтримка пагінації назад
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, PostData>): String? {
        // Використовується для визначення ключа для повторного завантаження даних
        // Можна повернути позицію, яка була останньою доступною
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.nextKey
        }
    }
}