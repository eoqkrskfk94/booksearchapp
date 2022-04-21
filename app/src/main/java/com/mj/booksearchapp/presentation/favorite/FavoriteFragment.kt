package com.mj.booksearchapp.presentation.favorite

import android.annotation.SuppressLint
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mj.booksearchapp.databinding.FragmentFavoriteBinding
import com.mj.booksearchapp.presentation.base.BaseFragment
import com.mj.booksearchapp.presentation.main.MainViewModel
import com.mj.booksearchapp.util.provider.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<MainViewModel, FragmentFavoriteBinding>() {
    override val viewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    override fun getViewBinding(): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater)

    override fun initViews() {
    }


    override fun observeData() {


    }

    companion object {
        const val TAG = "MyListFragment"
        fun newInstance() = FavoriteFragment()
    }

}