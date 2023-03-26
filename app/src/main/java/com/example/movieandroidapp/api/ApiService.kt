package com.example.movieandroidapp.api

import com.example.movieandroidapp.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMoviesList(@Query("page") page: Int): Call<MoviesListResponse>

    @GET("movie/{movie_id}/reviews")
    fun getMoviesReviews(@Path("movie_id") id: Int): Call<MoviesReviewResponse>

    @GET("movie/top_rated")
    fun getMoviesTopRated(@Query("page") page: Int): Call<MoviesTopRatedResponse>

    @GET("movie/now_playing")
    fun getMoviesNowPlaying(@Query("page") page: Int): Call<MoviesNowPlayingResponse>


}