package com.mj.booksearchapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.mj.booksearchapp.R
import com.mj.booksearchapp.databinding.ActivityMainBinding
import com.mj.booksearchapp.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val viewModel: MainViewModel by viewModels()


    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
    }


    override fun observeData() {

    }


}

