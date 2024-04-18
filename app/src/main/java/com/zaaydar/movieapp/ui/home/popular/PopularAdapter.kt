package com.zaaydar.movieapp.ui.home.popular

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
import com.zaaydar.movieapp.util.Constants.genreMap
import com.zaaydar.movieapp.util.Constants.userUUID
import com.zaaydar.movieapp.util.checkIsFav
import com.zaaydar.movieapp.util.imageInto

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    private var popularMovies: MutableList<MoviesDto> = arrayListOf()
    var itemClick: (Int) -> Unit = {}

    class PopularViewHolder(private var binding: MoviesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviesDto) {
            with(binding) {

                tvMovieName.text = item.title
                item.voteAverage?.let {
                    rbRate.rating = it.toFloat() / 2
                }

                val genres = mutableListOf<String>()
                for ((id, genre) in genreMap) {
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

        holder.itemView.setOnClickListener {
            itemClick(popularMovies[position].id)
        }

    }

    fun updatePopularList(newPopularMovies: List<MoviesDto>) {
        val startPosition = popularMovies.size
        val filteredNewMovies = newPopularMovies.filter { !popularMovies.contains(it) }
        popularMovies.addAll(filteredNewMovies)
        notifyItemRangeInserted(startPosition, filteredNewMovies.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh() {
        for (item in popularMovies) {
            item.checkIsFav()
        }

        notifyDataSetChanged()
    }


}