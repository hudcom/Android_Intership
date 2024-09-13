package com.project.android_intership.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(private val items: List<String>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    // Створюємо ViewHolder, який визначає вигляд кожного елемента
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO: Створити ViewHolder
    }

    // Створюємо новий View для кожного елемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    // Зв'язуємо дані з ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Зв'язати дані
        /*holder.textView.text = items[position]*/
    }

    // Повертаємо кількість елементів
    override fun getItemCount() = items.size
}
