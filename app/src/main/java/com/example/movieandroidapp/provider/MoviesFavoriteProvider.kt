package com.example.movieandroidapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.movieandroidapp.db.DatabaseContract.AUTHORITY
import com.example.movieandroidapp.db.DatabaseContract.MoviesFavoriteColumns.Companion.CONTENT_URI
import com.example.movieandroidapp.db.DatabaseContract.MoviesFavoriteColumns.Companion.TABLE_NAME
import com.example.movieandroidapp.db.MoviesFavoriteHelper

class MoviesFavoriteProvider : ContentProvider() {
    companion object {
        private const val MOVIES_FAVORITE = 1
        private const val MOVIES_FAVORITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var moviesFavoriteHelper: MoviesFavoriteHelper

        init {
            sUriMatcher.apply {
                addURI(AUTHORITY, TABLE_NAME, MOVIES_FAVORITE)
                addURI(AUTHORITY, "$TABLE_NAME/#", MOVIES_FAVORITE_ID)
            }
        }
    }

    override fun onCreate(): Boolean {
        moviesFavoriteHelper = MoviesFavoriteHelper.getInstance(context as Context)
        moviesFavoriteHelper.open()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            MOVIES_FAVORITE -> moviesFavoriteHelper.queryAll()
            MOVIES_FAVORITE_ID -> moviesFavoriteHelper.queryByTitle(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (MOVIES_FAVORITE) {
            sUriMatcher.match(uri) -> moviesFavoriteHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (MOVIES_FAVORITE_ID) {
            sUriMatcher.match(uri) -> moviesFavoriteHelper
                .deleteByTitle(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val updated: Int = when (MOVIES_FAVORITE_ID) {
            sUriMatcher.match(uri) -> moviesFavoriteHelper
                .update(uri.lastPathSegment.toString(), values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }
}