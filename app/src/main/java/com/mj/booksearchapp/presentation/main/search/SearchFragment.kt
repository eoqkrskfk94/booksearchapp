package com.mj.booksearchapp.presentation.main.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
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

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    private lateinit var bookInfoRecyclerViewAdapter: BookInfoListAdapter


    override fun getViewBinding(): FragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater)

    override fun initViews() {
        viewModel.setCurrentFragment(SearchFragment.TAG)
        setBookInfoRecyclerview()
        setSearchEditText()
    }

    override fun observeData() {

        viewModel.dataLoading.observe(viewLifecycleOwner) {
            binding.progressSearch.isVisible = it
        }

        viewModel.bookInfoListPaging.observe(viewLifecycleOwner) {
            bookInfoRecyclerViewAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        viewModel.saveFavoritePosition.observe(viewLifecycleOwner) {
            bookInfoRecyclerViewAdapter.snapshot()[it]?.favorite = true
            bookInfoRecyclerViewAdapter.notifyItemChanged(it)
        }

        viewModel.deleteFavoritePosition.observe(viewLifecycleOwner) {
            bookInfoRecyclerViewAdapter.snapshot()[it]?.favorite = false
            bookInfoRecyclerViewAdapter.notifyItemChanged(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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
                when (loadStates.refresh) {
                    is LoadState.Error -> {
                        Toast.makeText(requireContext(), getString(R.string.error_images_loading), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        recyclerviewBookInfo.adapter = bookInfoRecyclerViewAdapter
    }

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