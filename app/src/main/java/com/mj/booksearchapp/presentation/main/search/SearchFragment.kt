package com.mj.booksearchapp.presentation.main.search

import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.mj.booksearchapp.R
import com.mj.booksearchapp.databinding.FragmentSearchBinding
import com.mj.booksearchapp.presentation.base.BaseFragment
import com.mj.booksearchapp.presentation.main.MainViewModel
import com.mj.booksearchapp.presentation.main.detail.DetailFragment
import com.mj.booksearchapp.presentation.main.search.adapter.BookInfoListAdapter
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

        viewModel.saveFavoritePosition.observe(viewLifecycleOwner) {
            bookInfoRecyclerViewAdapter.snapshot()[it]?.favorite = true
            bookInfoRecyclerViewAdapter.notifyItemChanged(it)
        }

        viewModel.deleteFavoritePosition.observe(viewLifecycleOwner) {
            bookInfoRecyclerViewAdapter.snapshot()[it]?.favorite = false
            bookInfoRecyclerViewAdapter.notifyItemChanged(it)
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

        bookInfoRecyclerViewAdapter = BookInfoListAdapter(resourcesProvider) { bookInfo, position, _ ->

            val detailFragment = DetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("bookInfo", bookInfo)
            bundle.putInt("position", position)
            detailFragment.arguments = bundle

            showFragment(detailFragment, DetailFragment.TAG)
        }
        recyclerviewImageInfo.adapter = bookInfoRecyclerViewAdapter
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


    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

}