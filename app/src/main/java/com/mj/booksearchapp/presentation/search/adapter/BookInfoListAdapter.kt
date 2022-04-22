package com.mj.booksearchapp.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mj.booksearchapp.R
import com.mj.booksearchapp.databinding.ViewholderBookInfoBinding
import com.mj.booksearchapp.domain.model.BookInfo
import com.mj.booksearchapp.util.commaString
import com.mj.booksearchapp.util.getDateString
import com.mj.booksearchapp.util.provider.ResourcesProvider


class BookInfoListAdapter(
    private val resourcesProvider: ResourcesProvider,
    private val itemClick: (BookInfo, Int) -> Unit
) : PagingDataAdapter<BookInfo, BookInfoListAdapter.PagingViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val binding = ViewholderBookInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it, position) }
    }

    inner class PagingViewHolder(private val binding: ViewholderBookInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bookInfo: BookInfo, position: Int) = with(binding) {
            textviewBookTitle.text = bookInfo.title
            //textviewDatetime.text = getDateString(bookInfo.datetime, resourcesProvider.getString(R.string.iso_date_format), resourcesProvider.getString(R.string.date_format))
            textviewAuthor.text = if(bookInfo.authors.isNotEmpty()) bookInfo.authors[0] else null
            textviewPublisher.text = bookInfo.publisher
            textviewPrice.text = String.format(resourcesProvider.getString(R.string.price), bookInfo.price.commaString)
            textviewStatus.text = bookInfo.status

            Glide.with(root)
                .load(bookInfo.thumbnail)
                .placeholder(R.color.dark_gray)
                .error(R.color.dark_gray)
                .into(imageviewThumbnail)

            imageviewBookmark.setOnClickListener { itemClick(bookInfo, position) }

//            when (imageInfo.bookmark) {
//                true -> imageviewBookmark.setImageResource(R.drawable.ic_bookmark)
//                false -> imageviewBookmark.setImageResource(R.drawable.ic_bookmark_border)
//            }
        }

    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<BookInfo>() {

            override fun areItemsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
                return oldItem == newItem
            }
        }
    }


}

