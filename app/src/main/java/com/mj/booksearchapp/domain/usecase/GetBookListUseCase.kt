package com.mj.booksearchapp.domain.usecase

import androidx.paging.PagingData
import com.mj.booksearchapp.data.repository.BookRepository
import com.mj.booksearchapp.domain.model.BookInfo
import com.mj.booksearchapp.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {

//    suspend operator fun invoke(searchString: String): Result<Flow<PagingData<BookInfo>>> {
//        return bookRepository.getBookList(searchString)
//    }

    operator fun invoke(searchString: String): Flow<PagingData<BookInfo>> {
        return bookRepository.getBookList2(searchString)
    }

}