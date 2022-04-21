//package com.mj.searchapp.presentation.main.search.adapter
//
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.mj.searchapp.databinding.ViewholderSearchHistoryBinding
//
//class SearchHistoryListAdapter(
//    private val itemClick: (String, String, Int) -> Unit
//) : ListAdapter<String, SearchHistoryListAdapter.ListViewHolder>(diffCallback) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryListAdapter.ListViewHolder {
//        val binding = ViewholderSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ListViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        holder.bind(getItem(position), position)
//    }
//
//
//    inner class ListViewHolder(private val binding: ViewholderSearchHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(searchTitle: String, position: Int) = with(binding) {
//            textviewTitle.text = searchTitle
//
//            constraintlayoutSearchHistory.setOnClickListener { itemClick(ITEM_CLICK, searchTitle, position) }
//            imageviewCancel.setOnClickListener { itemClick(ITEM_DELETE_CLICK, searchTitle, position) }
//        }
//
//    }
//
//
//    companion object {
//        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
//
//            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//                return oldItem == newItem
//            }
//
//            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
//                return oldItem == newItem
//            }
//        }
//
//        const val ITEM_CLICK = "itemClick"
//        const val ITEM_DELETE_CLICK = "itemDeleteClick"
//    }
//
//
//}