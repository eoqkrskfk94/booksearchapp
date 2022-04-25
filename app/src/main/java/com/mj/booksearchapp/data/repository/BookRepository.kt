package com.mj.booksearchapp.data.repository

import androidx.paging.PagingData
import com.mj.booksearchapp.domain.model.BookInfo
import com.mj.booksearchapp.util.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun getBookList(searchString: String): Result<Flow<PagingData<BookInfo>>>

    fun getBookList2(searchString: String): Flow<PagingData<BookInfo>>

}