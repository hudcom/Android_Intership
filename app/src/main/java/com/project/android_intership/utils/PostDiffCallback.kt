package com.project.android_intership.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.android_intership.data.model.PostData

class PostDiffCallback(
    private val oldList: List<PostData>,
    private val newList: List<PostData>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].title == newList[newItemPosition].title

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}