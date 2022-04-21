//package com.mj.searchapp.presentation.main.search.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.paging.PagingDataAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.mj.searchapp.R
//import com.mj.searchapp.databinding.ViewholderImageInfoBinding
//import com.mj.searchapp.domain.model.ImageInfo
//import com.mj.searchapp.presentation.main.search.adapter.ImageInfoListAdapter.*
//import com.mj.searchapp.util.getDateString
//import com.mj.searchapp.util.provider.ResourcesProvider
//
//class ImageInfoListAdapter(
//    private val resourcesProvider: ResourcesProvider,
//    private val itemClick: (ImageInfo, Int) -> Unit
//) : PagingDataAdapter<ImageInfo, PagingViewHolder>(diffCallback) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
//        val binding = ViewholderImageInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return PagingViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
//        val item = getItem(position)
//        item?.let { holder.bind(it, position) }
//    }
//
//    inner class PagingViewHolder(private val binding: ViewholderImageInfoBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(imageInfo: ImageInfo, position: Int) = with(binding) {
//            textviewType.text = imageInfo.type
//            textviewDatetime.text = getDateString(imageInfo.dataTime, resourcesProvider.getString(R.string.iso_date_format), resourcesProvider.getString(R.string.date_format))
//
//            Glide.with(root)
//                .load(imageInfo.thumbnail)
//                .placeholder(R.color.dark_gray)
//                .error(R.color.dark_gray)
//                .into(imageviewThumbnail)
//
//            imageviewBookmark.setOnClickListener { itemClick(imageInfo, position) }
//
//            when (imageInfo.bookmark) {
//                true -> imageviewBookmark.setImageResource(R.drawable.ic_bookmark)
//                false -> imageviewBookmark.setImageResource(R.drawable.ic_bookmark_border)
//            }
//        }
//
//    }
//
//
//    companion object {
//        private val diffCallback = object : DiffUtil.ItemCallback<ImageInfo>() {
//
//            override fun areItemsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
//                return oldItem.thumbnail == newItem.thumbnail
//            }
//
//            override fun areContentsTheSame(oldItem: ImageInfo, newItem: ImageInfo): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//
//
//}
//
