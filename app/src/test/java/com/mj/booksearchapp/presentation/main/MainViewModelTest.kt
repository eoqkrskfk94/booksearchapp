package com.mj.booksearchapp.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mj.booksearchapp.data.repository.BookRepository
import com.mj.booksearchapp.domain.usecase.GetBookListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {


    @get:Rule
    var instantExceptionRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        val fakeBookRepository = FakeBookRepository()
        val getBookListUseCase = GetBookListUseCase(fakeBookRepository)
        viewModel = MainViewModel(getBookListUseCase)
    }

    @Test
    fun setCurrentFragmentTest() {
        viewModel.setCurrentFragment("TEST")

        val expected = "TEST"
        assertThat(viewModel.currentFragmentTag.value).isEqualTo(expected)

    }
}