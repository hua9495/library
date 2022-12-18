package com.alexchan.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.alexchan.library.databinding.ItemBookBinding

class BookAdapter(private val listener: Listener) : ListAdapter<Book, BookAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
) {

    interface Listener {
        fun onFavoriteButtonClicked(book_id: Int?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book) = with(binding) {
            bookTextView.text = item.title
            Glide.with(root.context)
                .load(item.bookCoverUrl)
                .into(bookImageView)
            favoriteImageButton.setImageResource(
                if (item.isFavorited) R.drawable.ic_favorite else R.drawable.ic_unfavorite
            )
            favoriteImageButton.setOnClickListener { listener.onFavoriteButtonClicked(item.book_id) }
        }
    }
}