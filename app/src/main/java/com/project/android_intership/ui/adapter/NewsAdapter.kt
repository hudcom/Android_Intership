package com.project.android_intership.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.android_intership.R
import com.project.android_intership.data.model.PostData
import com.project.android_intership.utils.PostDiffCallback
import java.util.concurrent.TimeUnit

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var items: List<PostData> = emptyList()

    // Створюємо ViewHolder, який визначає вигляд кожного елемента
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postTitle: TextView = itemView.findViewById(R.id.postTitle)
        val postContent: TextView = itemView.findViewById(R.id.postContent)
        val postAuthor: TextView = itemView.findViewById(R.id.postAuthor)
        val postDate: TextView = itemView.findViewById(R.id.postDate)
        val postComments: TextView = itemView.findViewById(R.id.postComments)
        val authorIcon: ImageView = itemView.findViewById(R.id.authorIcon)
        val postImage: ImageView = itemView.findViewById(R.id.postImage)
    }

    // Створюємо новий View для кожного елемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_news, parent, false)
        return ViewHolder(view)
    }

    // Зв'язуємо дані з ViewHolder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = items[position]
        Log.d("TEST", "onBindViewHolder called for position: $position")
        linkedData(holder, post)
        loadImage(holder,post)
    }

    // Повертаємо кількість елементів
    override fun getItemCount() = items.size

    fun updatePosts(newItems: List<PostData>) {
        val diffCallback = PostDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = newItems
        diffResult.dispatchUpdatesTo(this) // Оновлення RecyclerView за допомогою DiffUtil
        Log.d("TEST", "Update posts in Adapter. Count: ${items.size}")
    }

    private fun formatTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diffInMillis = now - (timestamp * 1000)

        val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
        val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60
        val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)

        return when {
            diffInDays > 0 -> "$diffInDays days ago"
            diffInHours > 0 -> "$diffInHours hours ago"
            diffInMinutes > 0 -> "$diffInMinutes minutes ago"
            else -> "Just now"
        }
    }

    private fun loadImage(holder: ViewHolder, post: PostData){
        val postUrl = post.url
        if (postUrl != null && (postUrl.contains(".jpeg") || postUrl.contains(".png") || postUrl.contains(".jpg"))){
            Glide.with(holder.itemView.context)
                .load(post.url)
                .apply(RequestOptions())
                .into(holder.postImage)
            holder.postImage.visibility = View.VISIBLE
        }
    }
    private fun linkedData(holder: ViewHolder, post: PostData) {
        with(holder) {
            postTitle.text = post.title
            postContent.text = post.selftext
            postAuthor.text = post.author_fullname
            postDate.text = formatTimeAgo(post.created)
            postComments.text = "${post.num_comments} comments"
            authorIcon.setImageResource(R.drawable.ic_icon_placeholder)
        }
    }
}
