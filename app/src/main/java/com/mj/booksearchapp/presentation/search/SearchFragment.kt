package com.mj.booksearchapp.presentation.search

import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mj.booksearchapp.databinding.FragmentDetailBinding
import com.mj.booksearchapp.databinding.FragmentSearchBinding
import com.mj.booksearchapp.presentation.base.BaseFragment
import com.mj.booksearchapp.presentation.main.MainViewModel
import com.mj.booksearchapp.presentation.search.adapter.BookInfoListAdapter
import com.mj.booksearchapp.util.provider.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<MainViewModel, FragmentSearchBinding>() {

    override val viewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    private lateinit var bookInfoRecyclerViewAdapter: BookInfoListAdapter


    override fun getViewBinding(): FragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater)

    override fun initViews() {

        setImageInfoRecyclerview()
        setSearchEditText()


    }

    override fun observeData() {

        viewModel.dataLoading.observe(viewLifecycleOwner) {
            binding.progressSearch.isVisible = it
        }

        viewModel.bookInfoListPaging.observe(viewLifecycleOwner) {
            bookInfoRecyclerViewAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun setSearchEditText() = with(binding) {
        setSearchTextDeleteButton()
        edittextSearch.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    viewModel.getBookList(text.toString().trim())
                    imageviewDelete.isVisible = true
                } else {
                    //검색창이 비워있을때 최근 검색어 창 보여주기
                    imageviewDelete.isGone = true
                }
            }
        }
    }

    private fun setSearchTextDeleteButton() = with(binding) {
        imageviewDelete.setOnClickListener {
            edittextSearch.setText("")
            recyclerviewImageInfo.isVisible = false
            imageviewDelete.isGone = true
        }
    }

    private fun setImageInfoRecyclerview() = with(binding) {
        recyclerviewImageInfo.layoutManager = LinearLayoutManager(requireContext())
        recyclerviewImageInfo.itemAnimator = null

        bookInfoRecyclerViewAdapter = BookInfoListAdapter(resourcesProvider) { bookInfo, position ->

        }
        recyclerviewImageInfo.adapter = bookInfoRecyclerViewAdapter
    }


    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

}