package com.mj.booksearchapp.presentation.search

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.paging.PagingData
import com.mj.booksearchapp.databinding.FragmentSearchBinding
import com.mj.booksearchapp.presentation.base.BaseFragment
import com.mj.booksearchapp.presentation.main.MainViewModel
import com.mj.booksearchapp.util.provider.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<MainViewModel, FragmentSearchBinding>() {

    override val viewModel: MainViewModel by activityViewModels()


    @Inject
    lateinit var resourcesProvider: ResourcesProvider


    override fun getViewBinding(): FragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater)

    override fun initViews() {

    }

    override fun observeData() {

    }


    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

}