package com.project.android_intership.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.android_intership.R
import com.project.android_intership.data.model.PostData
import com.project.android_intership.databinding.FragmentNewsBinding
import com.project.android_intership.ui.view.viewmodel.NewsViewModel
import com.project.android_intership.utils.ImageFormatException
import com.project.android_intership.utils.ImageDataCallback
import com.project.android_intership.utils.PostDataDiffCallback
import java.util.concurrent.TimeUnit

class NewsAdapter(private val viewModel: NewsViewModel,
                  private val onImageLongClickListener: (View) -> Unit,
) : PagingDataAdapter<PostData, NewsAdapter.ViewHolder>(PostDataDiffCallback()) {

    private val imageDataCallback: ImageDataCallback = viewModel

    // ViewHolder з DataBinding
    class ViewHolder(val binding: FragmentNewsBinding) : RecyclerView.ViewHolder(binding.root)

    // Створюємо новий View для кожного елемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Зв'язуємо дані з ViewHolder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        if (post != null){
            resetImage(holder)
            linkedData(holder, post)
            loadImage(holder,post)
            setClickListener(holder, post)
            setLongClickListener(holder,post)
        } else
            Log.d("TEST","Post is null")
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
                .into(holder.binding.postImage)
            holder.binding.postImage.visibility = View.VISIBLE
        }
    }
    private fun linkedData(holder: ViewHolder, post: PostData) {
        with(holder.binding) {
            postTitle.text = post.title
            postContent.text = post.selftext
            postAuthor.text = post.author_fullname
            postDate.text = formatTimeAgo(post.created)
            postComments.text = "${post.num_comments} comments"
            authorIcon.setImageResource(R.drawable.ic_icon_placeholder)
        }
    }

    // Скидання зображення перед завантаженням нового
    private fun resetImage(holder: ViewHolder){
        holder.binding.postImage.setImageDrawable(null)
        holder.binding.postImage.visibility = View.GONE
    }

    // Налаштовуємо натискання на зображення
    private fun setClickListener(holder: ViewHolder,post: PostData){
        holder.binding.postImage.setOnClickListener {
            Log.d("TEST", "Image clicked")
            viewModel.onImageClicked(post.url.toString())
        }
    }

    private fun setLongClickListener(holder:ViewHolder,post: PostData){
        holder.binding.postImage.setOnLongClickListener {
            Log.d("TEST", "Image long clicked")
            sendDataToViewModel(holder,post)
            onImageLongClickListener.invoke(holder.binding.postImage)
            true
        }
    }

    private fun sendDataToViewModel(holder:ViewHolder, post: PostData){
        val bitmap = convertImageToBitmap(holder)
        val format = getFormat(post) ?: throw ImageFormatException("It is not possible to specify the image format")
        imageDataCallback.sendImageData(bitmap,format)
    }
    private fun convertImageToBitmap(holder:ViewHolder): Bitmap = (holder.binding.postImage.drawable as BitmapDrawable).bitmap

    private fun getFormat(post: PostData): String? {
        return post.url?.split(".")?.lastOrNull()
    }
}
