package com.pbd.perludilindungi.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface BookmarkDao {
    @Insert
    suspend fun addBookmark(bookmark : Bookmark)

    @Update
    suspend fun updateBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks")
    suspend fun getBookmarks(bookmark: Bookmark) : List<Bookmark>

    @Query("SELECT * FROM bookmarks WHERE faskes_id = (:faskesId)")
    suspend fun getBookmarkByFaskesId(faskesId: Int): Bookmark

}