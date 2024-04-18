package com.zaaydar.movieapp.ui.profile.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.zaaydar.movieapp.R
import com.zaaydar.movieapp.databinding.MoviesRowBinding
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.util.Constants
import com.zaaydar.movieapp.util.imageInto

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var favoritesMovies: MutableList<MoviesDto> = arrayListOf()
    var itemClick: (Int) -> Unit = {}

    class FavoritesViewHolder(private var binding: MoviesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviesDto) {
            with(binding) {

                tvMovieName.text = item.title
                item.voteAverage?.let {
                    rbRate.rating = it.toFloat() / 2
                }

                val genres = mutableListOf<String>()
                for ((id, genre) in Constants.genreMap) {
                    if (item.genreIds.contains(id)) {
                        genres.add(genre)
                    }
                }
                tvMovieCategories.text = genres.joinToString(", ")
                item.posterPath?.let {
                    binding.root.context.imageInto(it, iwMovie)
                }

                checkIwFav(item, iwFav)

                iwFav.setOnClickListener {
                    if (Constants.favorites.contains(item.id.toLong())) Constants.favorites.remove(
                        item.id.toLong()
                    )
                    else Constants.favorites.add(item.id.toLong())
                    item.isFavorite = !item.isFavorite
                    checkIwFav(item, iwFav)

                    FirebaseFirestore.getInstance()
                        .collection("favoriteMovies")
                        .document(Constants.userUUID)
                        .set(hashMapOf("favs" to Constants.favorites))
                }
            }
        }

        private fun checkIwFav(item: MoviesDto, iwFav: ImageView) {
            if (item.isFavorite) iwFav.setImageResource(R.drawable.redheart)
            else iwFav.setImageResource(R.drawable.emptyheart)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            MoviesRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return favoritesMovies.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favoritesMovies[position])

        holder.itemView.setOnClickListener {
            itemClick(favoritesMovies[position].id)
        }
    }

    fun updateFavoriteMoviesList(newFavoriteMovies: List<MoviesDto>) {
        val startPosition = favoritesMovies.size
        val filteredNewMovies = newFavoriteMovies.filter { !favoritesMovies.contains(it) }
        favoritesMovies.addAll(filteredNewMovies)
        notifyItemRangeInserted(startPosition, filteredNewMovies.size)
    }
}