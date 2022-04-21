package com.mj.booksearchapp.data.entity.mapper

import com.mj.booksearchapp.data.entity.BookEntity
import com.mj.booksearchapp.domain.model.BookInfo

fun BookEntity.toBookInfo() = BookInfo(
    title = title,
    contents = contents,
    url = url,
    isbn = isbn,
    datetime = datetime,
    authors = authors,
    publisher = publisher,
    translators = translators,
    price = price,
    salePrice = salePrice,
    thumbnail = thumbnail,
    status = status,
    favorite = false
)

fun List<BookEntity>.toBookInfoList() = map { it.toBookInfo() }