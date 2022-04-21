package com.mj.booksearchapp.data.response

import com.google.gson.annotations.SerializedName
import com.mj.booksearchapp.data.entity.BookEntity

data class BookListResponse(
    val meta: Meta,
    val documents: List<BookEntity>
) {
    data class Meta(
        @SerializedName("total_count") val totalCount: Int,
        @SerializedName("pageable_count") val pageableCount: Int,
        @SerializedName("is_end") val isEnd: Boolean,
    )
}
