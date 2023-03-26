package com.example.movieandroidapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.size.Scale
import com.example.movieandroidapp.databinding.ActivityMoviesDetailBinding
import com.example.movieandroidapp.db.DatabaseContract
import com.example.movieandroidapp.db.MoviesFavoriteHelper
import com.example.movieandroidapp.entity.Movies
import com.example.movieandroidapp.model.MoviesListResponse
import com.example.movieandroidapp.model.MoviesNowPlayingResponse
import com.example.movieandroidapp.model.MoviesReviewResponse
import com.example.movieandroidapp.model.MoviesTopRatedResponse
import com.example.movieandroidapp.repository.MainRepository
import com.example.movieandroidapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MoviesDetailActivity : AppCompatActivity(),OnClickListener {
    companion object {
        const val RESULT_ADD = 101
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
    }

    lateinit var binding: ActivityMoviesDetailBinding
    private lateinit var moviesFavoriteHelper: MoviesFavoriteHelper
    private var movies: Movies? = null
    private var position: Int = 0

    @Inject
    lateinit var apiRepository: MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moviesID = intent.getIntExtra("EXTRA_ID", 1)
        val moviesName = intent.getStringExtra("EXTRA_NAME")
        val moviesRate = intent.getFloatExtra("EXTRA_RATING", 1.0F)
        val moviesDesc = intent.getStringExtra("EXTRA_DESC")
        val moviesIMG = intent.getStringExtra("EXTRA_IMG_POPULAR")

        moviesFavoriteHelper = MoviesFavoriteHelper.getInstance(applicationContext)
        moviesFavoriteHelper.open()

        movies = intent.getParcelableExtra(EXTRA_NOTE)
        if (movies != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
        } else {
            movies = Movies()
        }

        binding.fabFavorite.setOnClickListener(this)
        binding.fabShare.setOnClickListener(this)
        binding.fabFavorite.setImageResource(R.drawable.ic_favorite_empty)

        binding.apply {

            apiRepository.popularListCall(1).enqueue(object : Callback<MoviesListResponse> {
                override fun onResponse(
                    call: Call<MoviesListResponse>,
                    response: Response<MoviesListResponse>
                ) {
                    when (response.code()){
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        val moviePosterURL = Constants.POSTER_BASE_URL + moviesIMG
                                        binding.imageDetail.load(moviePosterURL){
                                            crossfade(true)
                                            placeholder(R.drawable.poster_placeholder)
                                            scale(Scale.FILL)
                                        }
                                        binding.tvTitleDetail.text = moviesName
                                        binding.tvRatingDetail.text = moviesRate.toString()
                                        binding.tvDescriptionDetail.text = moviesDesc
                                    }
                                }
                            }
                        }
                        in 300..399 -> {
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    Log.d(ContentValues.TAG, t.message.toString())

                }

            })

            apiRepository.topRatedCall(1).enqueue(object : Callback<MoviesTopRatedResponse> {
                override fun onResponse(
                    call: Call<MoviesTopRatedResponse>,
                    response: Response<MoviesTopRatedResponse>
                ) {
                    when (response.code()){
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        val moviePosterURL = Constants.POSTER_BASE_URL + moviesIMG
                                        binding.imageDetail.load(moviePosterURL){
                                            crossfade(true)
                                            placeholder(R.drawable.poster_placeholder)
                                            scale(Scale.FILL)
                                        }
                                        binding.tvTitleDetail.text = moviesName
                                        binding.tvRatingDetail.text = moviesRate.toString()
                                        binding.tvDescriptionDetail.text = moviesDesc
                                    }
                                }
                            }
                        }
                        in 300..399 -> {
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesTopRatedResponse>, t: Throwable) {
                    Log.d(ContentValues.TAG, t.message.toString())

                }

            })

            apiRepository.nowplayingCall(1).enqueue(object : Callback<MoviesNowPlayingResponse> {
                override fun onResponse(
                    call: Call<MoviesNowPlayingResponse>,
                    response: Response<MoviesNowPlayingResponse>
                ) {
                    when (response.code()){
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        val moviePosterURL = Constants.POSTER_BASE_URL + moviesIMG
                                        binding.imageDetail.load(moviePosterURL){
                                            crossfade(true)
                                            placeholder(R.drawable.poster_placeholder)
                                            scale(Scale.FILL)
                                        }
                                        binding.tvTitleDetail.text = moviesName
                                        binding.tvRatingDetail.text = moviesRate.toString()
                                        binding.tvDescriptionDetail.text = moviesDesc
                                    }
                                }
                            }
                        }
                        in 300..399 -> {
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesNowPlayingResponse>, t: Throwable) {
                    Log.d(ContentValues.TAG, t.message.toString())

                }

            })

            apiRepository.moviesReviewsCall(moviesID).enqueue(object : Callback<MoviesReviewResponse> {
                override fun onResponse(
                    call: Call<MoviesReviewResponse>,
                    response: Response<MoviesReviewResponse>
                ) {
                    when (response.code()){
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                       binding.tvReviewDescription.text = itBody.results[0].content

                                    }
                                    else
                                    {
                                        binding.tvReviewDescription.text= "Review tidak tersedia"
                                    }
                                }
                            }
                        }
                        in 300..399 -> {
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesReviewResponse>, t: Throwable) {
                    Log.d(ContentValues.TAG, t.message.toString())

                }

            })
        }
    }

    private fun addUserFavorite() {
        val moviesName = intent.getStringExtra("EXTRA_NAME")
        val moviesRate = intent.getFloatExtra("EXTRA_RATING", 1.0F)
        val moviesDesc = intent.getStringExtra("EXTRA_DESC")
        val moviesIMG = intent.getStringExtra("EXTRA_IMG_POPULAR")

        movies?.name = moviesName
        movies?.rating = moviesRate.toInt()
        movies?.desc = moviesDesc
        movies?.image = moviesIMG

        val intent = Intent(this, MoviesFavoriteActivity::class.java)
        intent.putExtra(EXTRA_NOTE, movies)
        intent.putExtra(EXTRA_POSITION, position)

        val values = ContentValues().apply {
            put(DatabaseContract.MoviesFavoriteColumns.COLUMN_IMAGE, moviesIMG)
            put(DatabaseContract.MoviesFavoriteColumns.COLUMN_NAME, moviesName)
            put(DatabaseContract.MoviesFavoriteColumns.COLUMN_RATING, moviesRate)
            put(DatabaseContract.MoviesFavoriteColumns.COLUMN_DESC, moviesDesc)
        }

        val result = moviesFavoriteHelper.insert(values)
        if (result > 0) {
            movies?.id = result.toInt()
            setResult(RESULT_ADD, intent)
            finish()
        } else {
            Toast.makeText(this@MoviesDetailActivity, "Gagal menambah data", Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(this,
            "$moviesName ${getString(R.string.insert_movies)}",
            Toast.LENGTH_SHORT).show()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabFavorite -> {
                    addUserFavorite()
            }
            R.id.fabShare -> {
                val moviesName = intent.getStringExtra("EXTRA_NAME")
                val moviesRate = intent.getFloatExtra("EXTRA_RATING", 1.0F)
                val moviesDesc = intent.getStringExtra("EXTRA_DESC")

                val shareUser = "${"Film Favoritku"}:\n" +
                        "${"Judul"}: ${moviesName}\n" +
                        "${"Rating"}: ${moviesRate}\n" +
                        "${"Deskripsi"}: $moviesDesc"
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareUser)
                shareIntent.type = "text/html"
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_using)))
            }
        }
    }

}