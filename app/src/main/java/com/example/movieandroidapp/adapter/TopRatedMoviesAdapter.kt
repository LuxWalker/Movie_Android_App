package com.example.movieandroidapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.movieandroidapp.R
import com.example.movieandroidapp.databinding.ItemPosterBinding
import com.example.movieandroidapp.databinding.ItemTopratedBinding
import com.example.movieandroidapp.model.MoviesTopRatedResponse
import com.example.movieandroidapp.util.Constants
import javax.inject.Inject

class TopRatedMoviesAdapter @Inject constructor() : RecyclerView.Adapter<TopRatedMoviesAdapter.ViewHolder>() {

    private lateinit var binding: ItemTopratedBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemTopratedBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int =differ.currentList.size

    override fun getItemViewType(position: Int): Int = position
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: MoviesTopRatedResponse.ResultsItem) {
            binding.apply {
                tvTitle.text = item.title
                tvRating.text = item.voteAverage.toString()
                val moviePosterURL = Constants.POSTER_BASE_URL + item.posterPath
                imgMovie.load(moviePosterURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }

        }
    }

    private var onItemClickListener: ((MoviesTopRatedResponse.ResultsItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (MoviesTopRatedResponse.ResultsItem) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<MoviesTopRatedResponse.ResultsItem>() {
        override fun areItemsTheSame(oldItem: MoviesTopRatedResponse.ResultsItem, newItem: MoviesTopRatedResponse.ResultsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoviesTopRatedResponse.ResultsItem, newItem: MoviesTopRatedResponse.ResultsItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}