package com.mj.booksearchapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mj.booksearchapp.data.network.KakaoApiService
import com.mj.booksearchapp.domain.model.BookInfo
import com.mj.booksearchapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DefaultBookRepository @Inject constructor(
    private val kakaoApiService: KakaoApiService,
    private val ioDispatcher: CoroutineDispatcher
): BookRepository {


    override suspend fun getBookList(searchString: String): Result<Flow<PagingData<BookInfo>>> = withContext(ioDispatcher) {
        return@withContext try {


            Result.Success(Pager(PagingConfig(pageSize = 10)) {
                BookInfoPagingSource(searchString, ioDispatcher, kakaoApiService)
            }.flow)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}