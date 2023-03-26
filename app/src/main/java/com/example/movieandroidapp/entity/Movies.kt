package com.example.movieandroidapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movies(
    var id: Int = 0,
    var image: String? = null,
    var name: String? = null,
    var rating: Int? = null,
    var desc: String? = null
) : Parcelable