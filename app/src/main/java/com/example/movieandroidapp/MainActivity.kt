package com.example.movieandroidapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieandroidapp.adapter.NowPlayingAdapter
import com.example.movieandroidapp.adapter.PopularMoviesAdapter
import com.example.movieandroidapp.adapter.TopRatedMoviesAdapter
import com.example.movieandroidapp.databinding.ActivityMainBinding
import com.example.movieandroidapp.model.MoviesListResponse
import com.example.movieandroidapp.model.MoviesNowPlayingResponse
import com.example.movieandroidapp.model.MoviesTopRatedResponse
import com.example.movieandroidapp.repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var apiRepository: MainRepository

    @Inject
    lateinit var popularAdapter: PopularMoviesAdapter

    @Inject
    lateinit var topratedAdapter: TopRatedMoviesAdapter

    @Inject
    lateinit var nowPlayingAdapter: NowPlayingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            apiRepository.popularListCall(1).enqueue(object : Callback<MoviesListResponse> {
                override fun onResponse(call: Call<MoviesListResponse>, response: Response<MoviesListResponse>) {
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        popularAdapter.differ.submitList(itData)
                                        //Recycler
                                        rvPopular.apply {
                                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                                            adapter = popularAdapter
                                        }
                                        popularAdapter.setOnItemClickListener {
                                           val i = Intent(this@MainActivity, MoviesDetailActivity::class.java)
                                            i.putExtra("EXTRA_ID", it.id)
                                            i.putExtra("EXTRA_NAME", it.title)
                                            i.putExtra("EXTRA_RATING", it.voteAverage.toFloat())
                                            i.putExtra("EXTRA_DESC", it.overview)
                                            i.putExtra("EXTRA_IMG_POPULAR", it.posterPath)
                                            startActivity(i)
                                        }
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
                    Log.d(TAG,t.message.toString())

                }

            })

            apiRepository.topRatedCall(1).enqueue(object : Callback<MoviesTopRatedResponse> {
                override fun onResponse(call: Call<MoviesTopRatedResponse>, response: Response<MoviesTopRatedResponse>) {
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        topratedAdapter.differ.submitList(itData)
                                        //Recycler
                                        rvToprated.apply {
                                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                                            adapter = topratedAdapter
                                        }
                                        topratedAdapter.setOnItemClickListener {
                                            val i = Intent(this@MainActivity, MoviesDetailActivity::class.java)
                                            i.putExtra("EXTRA_ID", it.id)
                                            i.putExtra("EXTRA_NAME", it.title)
                                            i.putExtra("EXTRA_RATING", it.voteAverage.toString().toFloat())
                                            i.putExtra("EXTRA_DESC", it.overview)
                                            i.putExtra("EXTRA_IMG_POPULAR", it.posterPath)
                                            startActivity(i)
                                        }
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
                    Log.d(TAG,t.message.toString())

                }

            })

            apiRepository.nowplayingCall(1).enqueue(object : Callback<MoviesNowPlayingResponse> {
                override fun onResponse(call: Call<MoviesNowPlayingResponse>, response: Response<MoviesNowPlayingResponse>) {
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.results.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        nowPlayingAdapter.differ.submitList(itData)
                                        //Recycler
                                        rvNowPlaying.apply {
                                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                                            adapter = nowPlayingAdapter
                                        }
                                        nowPlayingAdapter.setOnItemClickListener {
                                            val i = Intent(this@MainActivity, MoviesDetailActivity::class.java)
                                            i.putExtra("EXTRA_ID", it.id)
                                            i.putExtra("EXTRA_NAME", it.title)
                                            i.putExtra("EXTRA_RATING", it.voteAverage.toString().toFloat())
                                            i.putExtra("EXTRA_DESC", it.overview)
                                            i.putExtra("EXTRA_IMG_POPULAR", it.posterPath)
                                            startActivity(i)
                                        }
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
                    Log.d(TAG,t.message.toString())

                }

            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                val intent = Intent(this@MainActivity, MoviesFavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}