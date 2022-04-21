package com.mj.booksearchapp.data.database.dao

import androidx.room.*
import com.mj.booksearchapp.data.database.data.Bookmark

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmarks")
    suspend fun getAll(): List<Bookmark>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookInfo: Bookmark)

    @Delete
    suspend fun delete(bookInfo: Bookmark)

    @Query("DELETE FROM bookmarks WHERE thumbnail = :thumbnail")
    suspend fun deleteUsingThumbnail(thumbnail: String)

}