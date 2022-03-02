package com.pbd.perludilindungi.room

import androidx.room.*

@Dao
interface BookmarkDao {
    @Insert
    suspend fun addBookmark(bookmark : Bookmark)

    @Update
    suspend fun updateBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks")
    suspend fun getBookmarks() : List<Bookmark>

    @Query("SELECT * FROM bookmarks WHERE faskes_id = (:faskesId)")
    suspend fun getBookmarkByFaskesId(faskesId: Int): Bookmark

}