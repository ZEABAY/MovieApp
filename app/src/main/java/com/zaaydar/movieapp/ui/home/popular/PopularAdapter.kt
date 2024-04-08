package com.zaaydar.movieapp.ui.home.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.MoviesRowBinding
import com.zaaydar.movieapp.model.PopularMoviesDto
import com.zaaydar.movieapp.util.Constants.genreMap
import com.zaaydar.movieapp.util.imageInto

class PopularAdapter(private var popularMovies: MutableList<PopularMoviesDto>) :
    RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    class PopularViewHolder(private var binding: MoviesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PopularMoviesDto) {
            with(binding) {

                tvMovieName.text = item.title
                rbRate.rating = item.voteAverage.toFloat() / 2

                val genres = mutableListOf<String>()
                for ((id, genre) in genreMap) {
                    if (item.genreIds.contains(id)) {
                        genres.add(genre)
                    }
                }
                tvMovieCategories.text = genres.joinToString(", ")

                binding.root.context.imageInto(item.posterPath, iwMovie)


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
        var updatedNewPopularMovies = newPopularMovies
        if (popularMovies.size > 1 && (popularMovies.last() == newPopularMovies.first())) {
            updatedNewPopularMovies = newPopularMovies.drop(1)
        }
        val startPosition = popularMovies.size
        popularMovies.addAll(updatedNewPopularMovies)
        notifyItemRangeInserted(startPosition, updatedNewPopularMovies.size)
    }


}