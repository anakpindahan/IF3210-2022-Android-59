package com.pbd.perludilindungi.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Bookmark::class],
    version = 1,
    exportSchema = false
)
abstract class BookmarkDB : RoomDatabase() {
    abstract fun bookmarkDao() : BookmarkDao
    companion object {

        @Volatile private var instance : BookmarkDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BookmarkDB::class.java,
            "bookmarks.db"
        ).build()

    }

}