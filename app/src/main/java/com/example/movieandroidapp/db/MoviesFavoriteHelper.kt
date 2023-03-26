package com.example.movieandroidapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
            "${DatabaseContract.MoviesFavoriteColumns._ID} ASC",
            null)
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }


    fun insert(values: ContentValues?): Long = database.insert(DATABASE_TABLE, null, values)

    fun update(id: String, values: ContentValues?): Int =
        database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))

}