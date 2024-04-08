package com.zaaydar.movieapp.ui.home.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.MoviesRowBinding
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.util.Constants
import com.zaaydar.movieapp.util.imageInto

class NowPlayingAdapter(private var nowPlayingMovies: MutableList<MoviesDto>) :
    RecyclerView.Adapter<NowPlayingAdapter.NowPlayingViewHolder>() {
    class NowPlayingViewHolder(private var binding: MoviesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviesDto) {
            with(binding) {

                tvMovieName.text = item.title
                rbRate.rating = item.voteAverage.toFloat() / 2

                val genres = mutableListOf<String>()
                for ((id, genre) in Constants.genreMap) {
                    if (item.genreIds.contains(id)) {
                        genres.add(genre)
                    }
                }
                tvMovieCategories.text = genres.joinToString(", ")

                binding.root.context.imageInto(item.posterPath, iwMovie)


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        return NowPlayingViewHolder(
            MoviesRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return nowPlayingMovies.size
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        holder.bind(nowPlayingMovies[position])
    }

    fun updateNowPlayingMoviesList(newNowPlayingMovies: List<MoviesDto>) {
        val startPosition = nowPlayingMovies.size
        val filteredNewMovies = newNowPlayingMovies.filter { !nowPlayingMovies.contains(it) }
        nowPlayingMovies.addAll(filteredNewMovies)
        notifyItemRangeInserted(startPosition, filteredNewMovies.size)
    }

}