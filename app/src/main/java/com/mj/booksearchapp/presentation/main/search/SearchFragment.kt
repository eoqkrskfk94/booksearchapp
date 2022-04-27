package com.mj.booksearchapp.presentation.main.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mj.booksearchapp.R
import com.mj.booksearchapp.databinding.FragmentSearchBinding
import com.mj.booksearchapp.presentation.base.BaseFragment
import com.mj.booksearchapp.presentation.main.MainViewModel
import com.mj.booksearchapp.presentation.main.detail.DetailFragment
import com.mj.booksearchapp.presentation.main.search.adapter.BookInfoListAdapter
import com.mj.booksearchapp.util.provider.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : BaseFragment<MainViewModel, FragmentSearchBinding>() {

    override val viewModel: MainViewModel by activityViewModels()
    override fun getViewBinding(): FragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater)

    @Inject
    lateinit var resourcesProvider: ResourcesProvider
    private lateinit var bookInfoRecyclerViewAdapter: BookInfoListAdapter

    override fun initViews() {
        viewModel.setCurrentFragment(SearchFragment.TAG)
        setBookInfoRecyclerview()
        setSearchEditText()
    }

    override fun observeData() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                bookInfoRecyclerViewAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.saveFavoritePosition.collectLatest {
                if (it == -1) return@collectLatest
                bookInfoRecyclerViewAdapter.snapshot()[it]?.favorite = true
                bookInfoRecyclerViewAdapter.notifyItemChanged(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.deleteFavoritePosition.collectLatest {
                if (it == -1) return@collectLatest
                bookInfoRecyclerViewAdapter.snapshot()[it]?.favorite = false
                bookInfoRecyclerViewAdapter.notifyItemChanged(it)
            }
        }
    }

    private fun setSearchEditText() = with(binding) {
        setSearchTextDeleteButton()
        edittextSearch.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty()) {
                    viewModel.searchBook(text.toString().trim())
                    imageviewDelete.isVisible = true
                    recyclerviewBookInfo.isVisible = true
                    groupSearch.isVisible = false
                } else { // 입력창이 배워 있을때
                    recyclerviewBookInfo.isVisible = false
                    imageviewDelete.isVisible = false
                    groupSearch.isVisible = true
                    groupError.isVisible = false
                }
            }
        }
    }


    private fun setSearchTextDeleteButton() = with(binding) {
        imageviewDelete.setOnClickListener {
            edittextSearch.setText("")
            recyclerviewBookInfo.isVisible = false
            imageviewDelete.isGone = true
        }
    }

    private fun setBookInfoRecyclerview() = with(binding) {
        recyclerviewBookInfo.layoutManager = LinearLayoutManager(requireContext())
        recyclerviewBookInfo.itemAnimator = null

        bookInfoRecyclerViewAdapter = BookInfoListAdapter(resourcesProvider) { bookInfo, position, _ ->

            val detailFragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("bookInfo", bookInfo)
                    putInt("position", position)
                }
            }
            closeKeyboard()
            showFragment(detailFragment, DetailFragment.TAG)
        }

        viewLifecycleOwner.lifecycleScope.launch {

            bookInfoRecyclerViewAdapter.loadStateFlow.collectLatest { loadStates ->

                // 검색 결과가 없을때
                if (loadStates.append.endOfPaginationReached) {
                    textviewNoResult.isVisible = bookInfoRecyclerViewAdapter.itemCount < 1
                } else {
                    textviewNoResult.isVisible = false
                }

                // 검색 중 오류가 발생했을때
                groupError.isVisible = loadStates.refresh is LoadState.Error

                // 검색 로딩 중일때
                progressSearch.isVisible = loadStates.refresh is LoadState.Loading

            }
        }
        recyclerviewBookInfo.adapter = bookInfoRecyclerViewAdapter
    }

    //해당 fragment는 숨기고 새로운 fragment 생성
    private fun showFragment(fragment: Fragment, tag: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .hide(this)
            .commitAllowingStateLoss()

        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .add(R.id.fragment_container_view, fragment, tag)
            .commitAllowingStateLoss()
    }


    private fun Fragment.closeKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

}