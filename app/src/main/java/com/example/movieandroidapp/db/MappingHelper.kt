package com.example.movieandroidapp.db

import android.database.Cursor
import android.provider.BaseColumns._ID
import com.example.movieandroidapp.entity.Movies

object MappingHelper {

    fun mapCursorToArrayList(movieCursor: Cursor?): ArrayList<Movies> {
        val movieList = ArrayList<Movies>()
        movieCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val image = getString(getColumnIndexOrThrow(DatabaseContract.MoviesFavoriteColumns.COLUMN_IMAGE))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.MoviesFavoriteColumns.COLUMN_NAME))
                val rating = getInt(getColumnIndexOrThrow(DatabaseContract.MoviesFavoriteColumns.COLUMN_RATING))
                val desc = getString(getColumnIndexOrThrow(DatabaseContract.MoviesFavoriteColumns.COLUMN_DESC))
                movieList.add(Movies(id, image, name, rating, desc))
            }
        }
        return movieList
    }
}