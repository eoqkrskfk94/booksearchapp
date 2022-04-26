package com.mj.booksearchapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mj.booksearchapp.R
import com.mj.booksearchapp.data.repository.BookRepository
import com.mj.booksearchapp.domain.model.BookInfo
import com.mj.booksearchapp.domain.usecase.GetBookListUseCase
import com.mj.booksearchapp.presentation.base.BaseViewModel
import com.mj.booksearchapp.util.Result
import com.mj.booksearchapp.util.getValue
import com.mj.booksearchapp.util.provider.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase
) : BaseViewModel() {

    private val _currentFragmentTag = MutableLiveData<String>()
    val currentFragmentTag: LiveData<String> = _currentFragmentTag

    private val _saveFavoritePosition = MutableStateFlow(-1)
    val saveFavoritePosition: StateFlow<Int> = _saveFavoritePosition

    private val _deleteFavoritePosition = MutableStateFlow(-1)
    val deleteFavoritePosition: StateFlow<Int> = _deleteFavoritePosition

    private val searchBookFlow = MutableSharedFlow<String>()

    private var prevSearchString = ""

    val pagingDataFlow = searchBookFlow
        .flatMapLatest {
            getBookList(it)
        }
        .cachedIn(viewModelScope)

    fun setCurrentFragment(tag: String) {
        _currentFragmentTag.value = tag
    }

    fun searchBook(searchString: String) {
        if (prevSearchString == searchString) return
        prevSearchString = searchString

        viewModelScope.launch {
            searchBookFlow.emit(searchString)
        }
    }

    private fun getBookList(searchString: String): Flow<PagingData<BookInfo>> {
        return getBookListUseCase(searchString)
    }

    fun saveFavorite(position: Int) {
        _saveFavoritePosition.value = position
    }

    fun deleteFavorite(position: Int) {
        _deleteFavoritePosition.value = position
    }

}