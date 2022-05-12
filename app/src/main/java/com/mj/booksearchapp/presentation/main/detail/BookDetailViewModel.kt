package com.mj.booksearchapp.presentation.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mj.booksearchapp.domain.model.BookInfo
import com.mj.booksearchapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor() : BaseViewModel() {

    private val _bookInfoLiveData = MutableLiveData<BookInfo>()
    val bookInfoLiveData: LiveData<BookInfo> = _bookInfoLiveData


    fun setBookInfo(bookInfo: BookInfo) {
        _bookInfoLiveData.value = bookInfo
    }

    fun setBookFavorite(favorite: Boolean) {
        _bookInfoLiveData.value?.favorite = favorite
    }

}