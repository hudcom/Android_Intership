package com.project.android_intership.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.android_intership.data.model.PostData

class PostDataDiffCallback : DiffUtil.ItemCallback<PostData>() {
    override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean {
        return oldItem == newItem
    }
}