package com.mj.booksearchapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.mj.booksearchapp.R
import com.mj.booksearchapp.databinding.ActivityMainBinding
import com.mj.booksearchapp.presentation.base.BaseActivity
import com.mj.booksearchapp.presentation.favorite.FavoriteFragment
import com.mj.booksearchapp.presentation.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val viewModel: MainViewModel by viewModels()


    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        setBottomNavigation()
        showFragment(SearchFragment.newInstance(), SearchFragment.TAG)
    }


    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction()
                //.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .hide(it)
                .commitAllowingStateLoss()
        }

        findFragment?.let {
            supportFragmentManager.beginTransaction()
                //.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .show(it)
                .commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, tag)
                .commitAllowingStateLoss()
        }
    }


    private fun setBottomNavigation() = with(binding) {
        bottomNavigation.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.search -> {
                        showFragment(SearchFragment.newInstance(), SearchFragment.TAG)
                    }

                    R.id.my_list -> {
                        showFragment(FavoriteFragment.newInstance(), FavoriteFragment.TAG)
                    }
                }
                true
            }
        }
    }

    override fun observeData() {

    }


}

