package com.zaaydar.movieapp.ui.search.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.zaaydar.movieapp.R
import com.zaaydar.movieapp.databinding.MoviesRowBinding
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.util.MySingleton.favorites
import com.zaaydar.movieapp.util.MySingleton.userUUID
import com.zaaydar.movieapp.util.imageInto

class CategoryResultAdapter : RecyclerView.Adapter<CategoryResultAdapter.CategoryViewHolder>() {

    private var categoryMovies: MutableList<MoviesDto> = arrayListOf()
    var itemClick: (Int) -> Unit = {}

    class CategoryViewHolder(private var binding: MoviesRowBinding) :
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            MoviesRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryMovies.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryMovies[position])

        holder.itemView.setOnClickListener {
            itemClick(categoryMovies[position].id)
        }
    }

    fun updateCategoryList(newCategoryMovies: List<MoviesDto>) {
        val startPosition = categoryMovies.size
        val filteredNewMovies = newCategoryMovies.filter { !categoryMovies.contains(it) }
        categoryMovies.addAll(filteredNewMovies)
        notifyItemRangeInserted(startPosition, filteredNewMovies.size)
    }


}