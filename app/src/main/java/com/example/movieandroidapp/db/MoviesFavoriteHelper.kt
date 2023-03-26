package com.example.movieandroidapp.db

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.movieandroidapp.db.DatabaseContract.MoviesFavoriteColumns.Companion.COLUMN_NAME
import com.example.movieandroidapp.db.DatabaseContract.MoviesFavoriteColumns.Companion.TABLE_NAME
import com.example.movieandroidapp.db.DatabaseContract.MoviesFavoriteColumns.Companion._ID

class MoviesFavoriteHelper(context: Context) {

    companion object {
       const val DATABASE_TABLE = TABLE_NAME
        var INSTANCE: MoviesFavoriteHelper? = null
        fun getInstance(context: Context): MoviesFavoriteHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) INSTANCE = MoviesFavoriteHelper(context)
                }
            }
            return INSTANCE as MoviesFavoriteHelper
        }
    }

    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun queryAll(): Cursor {
        return database.query(
            MoviesFavoriteHelper.DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_NAME COLLATE NOCASE",
            null)
    }

    fun queryByTitle(username: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$COLUMN_NAME = ?",
            arrayOf(username),
            null,
            null,
            null)
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }


    fun insert(values: ContentValues?): Long = database.insert(DATABASE_TABLE, null, values)

    fun deleteByTitle(title: String): Int =
        database.delete(DATABASE_TABLE,"$COLUMN_NAME = '$title'", null)

    fun update(id: String, values: ContentValues?): Int =
        database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))

    fun checkMoviesName(username: String): Boolean {
        val cursor = database.query(
            DATABASE_TABLE,
            null, "$COLUMN_NAME = ?",
            arrayOf(username),
            null,
            null,
            null)
        var check = false
        if (cursor.moveToFirst()) {
            check = true
            var cursorIndex = 0
            while (cursor.moveToNext()) cursorIndex++
            Log.d(TAG, "Film ditemukan: $cursorIndex")
        }
        cursor.close()
        return check
    }

}