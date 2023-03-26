package com.example.movieandroidapp.db

import android.provider.BaseColumns

object DatabaseContract {

    internal class MoviesFavoriteColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favorite_movies"
            const val _ID = "_id"
            const val COLUMN_IMAGE = "image_url"
            const val COLUMN_NAME = "name"
            const val COLUMN_RATING = "rating"
            const val COLUMN_DESC = "description"
        }
    }
}