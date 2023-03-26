package com.example.movieandroidapp.repository

import com.example.movieandroidapp.api.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun popularListCall(page: Int) = apiService.getPopularMoviesList(page)
    fun moviesReviewsCall(id: Int) = apiService.getMoviesReviews(id)
    fun topRatedCall(page: Int) = apiService.getMoviesTopRated(page)
    fun nowplayingCall(page: Int) = apiService.getMoviesNowPlaying(page)
}