package com.example.movieandroidapp.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.example.movieandroidapp.db.DatabaseContract.MoviesFavoriteColumns.Companion.COLUMN_DESC
import com.example.movieandroidapp.db.DatabaseContract.MoviesFavoriteColumns.Companion.COLUMN_IMAGE
import com.example.movieandroidapp.db.DatabaseContract.MoviesFavoriteColumns.Companion.COLUMN_NAME
import com.example.movieandroidapp.db.DatabaseContract.MoviesFavoriteColumns.Companion.COLUMN_RATING
import com.example.movieandroidapp.db.DatabaseContract.MoviesFavoriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val db = this.writableDatabase

    companion object {
        private const val DATABASE_NAME = "dbmoviesfavorite"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_USER_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COLUMN_IMAGE TEXT NOT NULL," +
                " $COLUMN_NAME TEXT NOT NULL," +
                " $COLUMN_RATING TEXT NOT NULL," +
                " $COLUMN_DESC TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun queryAll(): Cursor {
        return db.query(
            MoviesFavoriteHelper.DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null)
    }
}