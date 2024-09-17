package com.project.android_intership.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.android_intership.data.model.PostData

@Deprecated("Uses before adding PagingLibrary")
class PostDiffCallback(
    private val oldList: List<PostData>,
    private val newList: List<PostData>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}