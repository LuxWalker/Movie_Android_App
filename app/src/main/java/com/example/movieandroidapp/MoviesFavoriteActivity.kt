package com.example.movieandroidapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieandroidapp.adapter.FavoriteMoviesAdapter
import com.example.movieandroidapp.databinding.ActivityMoviesFavoriteBinding
import com.example.movieandroidapp.db.MappingHelper
import com.example.movieandroidapp.db.MoviesFavoriteHelper
import com.example.movieandroidapp.entity.Movies
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFavoriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityMoviesFavoriteBinding

    @Inject
    lateinit var adapter: FavoriteMoviesAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMovieFav.layoutManager = LinearLayoutManager(this)
        binding.rvMovieFav.setHasFixedSize(true)
        binding.rvMovieFav.adapter = adapter


        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Movies>(EXTRA_STATE)
            if (list != null) {
                adapter.listMovies = list
            }
        }
    }

    private fun loadNotesAsync() {
        lifecycleScope.launch {
            val moviesfavoriteHelper = MoviesFavoriteHelper.getInstance(applicationContext)
            moviesfavoriteHelper.open()
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = moviesfavoriteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val movies = deferredNotes.await()
            if (movies.size > 0) {
                adapter.listMovies = movies
            } else {
                adapter.listMovies = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
            moviesfavoriteHelper.close()

            adapter.notifyDataSetChanged()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listMovies)
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvMovieFav, message, Snackbar.LENGTH_SHORT).show()
    }
}
