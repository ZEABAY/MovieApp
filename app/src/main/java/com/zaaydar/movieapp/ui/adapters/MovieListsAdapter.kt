package com.zaaydar.movieapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.zaaydar.movieapp.R
import com.zaaydar.movieapp.databinding.MoviesRowBinding
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.util.Constants.favorites
import com.zaaydar.movieapp.util.Constants.userUUID
import com.zaaydar.movieapp.util.checkIsFav
import com.zaaydar.movieapp.util.imageInto

class MovieListsAdapter : RecyclerView.Adapter<MovieListsAdapter.MovieListsViewHolder>() {

    private var movies: MutableList<MoviesDto> = arrayListOf()
    var itemClick: (Int) -> Unit = {}

    class MovieListsViewHolder(private var binding: MoviesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviesDto) {
            with(binding) {

                tvMovieName.text = item.title
                tvMovieCategories.text = item.genreStrings
                rbRate.rating = item.voteAverage

                checkIwFav(item, iwFav)

                item.posterPath?.let {
                    binding.root.context.imageInto(it, iwMovie)
                }

                iwFav.setOnClickListener {
                    if (favorites.contains(item.id.toLong())) favorites.remove(item.id.toLong())
                    else favorites.add(item.id.toLong())
                    item.isFavorite = !item.isFavorite
                    checkIwFav(item, iwFav)

                    FirebaseFirestore.getInstance()
                        .collection("favoriteMovies")
                        .document(userUUID)
                        .set(hashMapOf("favs" to favorites))
                }
            }
        }

        private fun checkIwFav(item: MoviesDto, iwFav: ImageView) {
            if (item.isFavorite) iwFav.setImageResource(R.drawable.redheart)
            else iwFav.setImageResource(R.drawable.emptyheart)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListsViewHolder {
        return MovieListsViewHolder(
            MoviesRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieListsViewHolder, position: Int) {
        holder.bind(movies[position])

        holder.itemView.setOnClickListener {
            itemClick(movies[position].id)
        }

    }

    fun updateMoviesList(newMoviesMovies: List<MoviesDto>) {
        val startPosition = movies.size
        val filteredNewMovies = newMoviesMovies.filter { !movies.contains(it) }
        movies.addAll(filteredNewMovies)
        notifyItemRangeInserted(startPosition, filteredNewMovies.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh() {
        for (item in movies) {
            item.checkIsFav()
        }

        notifyDataSetChanged()
    }


}