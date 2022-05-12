package com.mj.booksearchapp.presentation.main

import androidx.paging.PagingData
import com.google.gson.annotations.SerializedName
import com.mj.booksearchapp.data.entity.BookEntity
import com.mj.booksearchapp.data.repository.BookRepository
import com.mj.booksearchapp.domain.model.BookInfo
import kotlinx.coroutines.flow.Flow

class FakeBookRepository : BookRepository {
    private val books = mutableListOf<BookEntity>()

    init {
        books.add(
            BookEntity(
                "book1",
                "contents1",
                "url1",
                "isbn1",
                "datetime1",
                listOf("author1"),
                "publisher1",
                listOf("translator1"),
                100,
                200,
                "thumbnail1",
                "status1"
            )
        )
        books.add(
            BookEntity(
                "book2",
                "contents2",
                "url2",
                "isbn2",
                "datetime2",
                listOf("author2"),
                "publisher2",
                listOf("translator2"),
                100,
                200,
                "thumbnail2",
                "status2"
            )
        )
    }


    override fun getBookPagingData(searchString: String): Flow<PagingData<BookInfo>> {
        TODO("Not yet implemented")
    }

    override fun getBookList(searchString: String): List<BookEntity> {
        return books
    }
}