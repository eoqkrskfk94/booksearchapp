package com.mj.booksearchapp.domain.model

import com.google.gson.annotations.SerializedName

data class BookInfo(
    val title: String,
    val contents: String,
    val url: String,
    val isbn: String,
    val datetime: String,
    val authors: List<String>,
    val publisher: String,
    val translators: List<String>,
    val price: Int,
    val salePrice: Int,
    val thumbnail: String,
    val status: String,
    var favorite: Boolean = false
)
