package com.mj.booksearchapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mj.booksearchapp.data.network.KakaoApiService
import com.mj.booksearchapp.domain.model.BookInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DefaultBookRepository @Inject constructor(
    private val kakaoApiService: KakaoApiService,
    private val ioDispatcher: CoroutineDispatcher
): BookRepository {

    override fun getBookList(searchString: String): Flow<PagingData<BookInfo>> {
        return Pager(PagingConfig(pageSize = 10)) {
            BookInfoPagingSource(searchString, ioDispatcher, kakaoApiService)
        }.flow
    }

}