package com.mj.booksearchapp.data.repository

import androidx.paging.PagingData
import com.mj.booksearchapp.domain.model.BookInfo
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBookList(searchString: String): Flow<PagingData<BookInfo>>
}