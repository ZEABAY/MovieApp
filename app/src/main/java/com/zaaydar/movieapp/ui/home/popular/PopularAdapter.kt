package com.zaaydar.movieapp.ui.home.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zaaydar.movieapp.R
import com.zaaydar.movieapp.databinding.MoviesRowBinding
import com.zaaydar.movieapp.model.PopularMoviesDto
import com.zaaydar.movieapp.util.Constants.POSTER_BASE_URL

class PopularAdapter(private var popularMovies: List<PopularMoviesDto>) :
    RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    class PopularViewHolder(private var binding: MoviesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PopularMoviesDto) {
            with(binding) {
                tvMovieName.text = item.title.toString()
                tvMovieCategories.text = item.genreIds.toString()
                tvMoviePopularity.text = item.popularity.toString()
                rbRate.rating = item.voteAverage.toFloat() / 2


                val placeholder = CircularProgressDrawable(binding.root.context).apply {
                    strokeWidth = 8f
                    centerRadius = 40f
                    start()
                }
                val options = RequestOptions().placeholder(placeholder)
                    .error(R.mipmap.ic_launcher_round)

                Glide.with(binding.root.context).setDefaultRequestOptions(options)
                    .load(POSTER_BASE_URL + item.posterPath).into(iwMovie)


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            MoviesRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return popularMovies.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.bind(popularMovies[position])
    }

    fun updatePopularList(newPopularMovies: List<PopularMoviesDto>) {
        popularMovies = newPopularMovies
        notifyDataSetChanged()
    }
}