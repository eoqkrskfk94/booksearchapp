package com.mj.booksearchapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.mj.booksearchapp.R
import com.mj.booksearchapp.databinding.ActivityMainBinding
import com.mj.booksearchapp.presentation.base.BaseActivity
import com.mj.booksearchapp.presentation.main.detail.DetailFragment
import com.mj.booksearchapp.presentation.main.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val viewModel: MainViewModel by viewModels()


    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initViews() {
        // test 5

        when(viewModel.currentFragmentTag.value) {
            null -> showFragment(SearchFragment.newInstance(), SearchFragment.TAG)
            SearchFragment.TAG -> showFragment(SearchFragment.newInstance(), SearchFragment.TAG)
            DetailFragment.TAG -> showFragment(DetailFragment.newInstance(), DetailFragment.TAG)
        }
    }

    override fun observeData() {

    }

    private fun showFragment(fragment: Fragment, tag: String) {

        val findFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction()
                .hide(it)
                .commitAllowingStateLoss()
        }

        findFragment?.let {
            supportFragmentManager.beginTransaction()
                .show(it)
                .commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container_view, fragment, tag)
                .commitAllowingStateLoss()
        }
    }


}

