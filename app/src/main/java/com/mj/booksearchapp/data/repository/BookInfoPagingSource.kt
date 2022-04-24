package com.mj.booksearchapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mj.booksearchapp.data.entity.mapper.toBookInfoList
import com.mj.booksearchapp.data.network.KakaoApiService
import com.mj.booksearchapp.domain.model.BookInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class BookInfoPagingSource(
    private val searchString: String,
    private val ioDispatcher: CoroutineDispatcher,
    private val kakaoApiService: KakaoApiService,
) : PagingSource<Int, BookInfo>() {

    override fun getRefreshKey(state: PagingState<Int, BookInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookInfo> {
        return try {
            val page = params.key ?: 1

            val bookList = withContext(ioDispatcher) {
                println("thread: ${Thread.currentThread().name}")
                kakaoApiService.getBookList(query = searchString, page = page, size = 50).body()?.documents?.toBookInfoList() ?: listOf()
            }

            LoadResult.Page(
                data = bookList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (bookList.isEmpty()) null else page + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}