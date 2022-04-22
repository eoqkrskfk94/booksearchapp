package com.mj.booksearchapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.mj.booksearchapp.R
import com.mj.booksearchapp.domain.model.BookInfo
import com.mj.booksearchapp.domain.usecase.GetBookListUseCase
import com.mj.booksearchapp.presentation.base.BaseViewModel
import com.mj.booksearchapp.util.Result
import com.mj.booksearchapp.util.getValue
import com.mj.booksearchapp.util.provider.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _bookInfoListPaging = MutableLiveData<PagingData<BookInfo>>()
    val bookInfoListPaging: LiveData<PagingData<BookInfo>> = _bookInfoListPaging

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error


    fun getBookList(searchString: String) {
        viewModelScope.launch {
            _dataLoading.value = true
            when (val result = getBookListUseCase(searchString)) {
                is Result.Success -> {
                    result.getValue().run {
                        this.collectLatest { pagingData ->
                            _bookInfoListPaging.value = pagingData
                            _dataLoading.value = false
                        }
                    }
                }
                is Result.Error -> {
                    _error.value = resourcesProvider.getString(R.string.error_images_loading)
                    _dataLoading.value = false
                }
            }

        }
    }

}