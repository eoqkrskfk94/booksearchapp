package com.mj.booksearchapp.data.repository

import androidx.paging.PagingData
import com.mj.booksearchapp.data.entity.BookEntity
import com.mj.booksearchapp.domain.model.BookInfo
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBookPagingData(searchString: String): Flow<PagingData<BookInfo>>

    fun getBookList(searchString: String): List<BookEntity>
}