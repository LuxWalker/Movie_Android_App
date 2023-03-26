package com.example.movieandroidapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.movieandroidapp.R
import com.example.movieandroidapp.databinding.ItemFavoriteBinding
import com.example.movieandroidapp.entity.Movies
import com.example.movieandroidapp.util.Constants.POSTER_BASE_URL
import javax.inject.Inject

class FavoriteMoviesAdapter @Inject() constructor() :
    RecyclerView.Adapter<FavoriteMoviesAdapter.TeamViewHolder>() {
    var listMovies = ArrayList<Movies>()
        set(listMovies) {
            if (listMovies.size > 0) {
                this.listMovies.clear()
            }
            this.listMovies.addAll(listMovies)
        }

    fun addItem(movies: Movies) {
        this.listMovies.add(movies)
        notifyItemInserted(this.listMovies.size - 1)
    }

    fun updateItem(position: Int, movies: Movies) {
        this.listMovies[position] = movies
        notifyItemChanged(position, movies)
    }

    fun removeItem(position: Int) {
        this.listMovies.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovies.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int = this.listMovies.size

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFavoriteBinding.bind(itemView)
        fun bind(movies: Movies) {
            binding.apply {
                val moviePosterURL = POSTER_BASE_URL + movies.image
                binding.imgMovieFav.load(moviePosterURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }

            }
            binding.tvTitleFav.text = movies.name
            binding.tvRatingFav.text = movies.rating.toString()
            binding.tvDescFav.text = movies.desc
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedNote: Movies?, position: Int?)
    }
}