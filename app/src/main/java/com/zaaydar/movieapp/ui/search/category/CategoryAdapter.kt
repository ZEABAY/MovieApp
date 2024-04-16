package com.zaaydar.movieapp.ui.search.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.MoviesRowBinding
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.util.Constants
import com.zaaydar.movieapp.util.imageInto

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryMovies: MutableList<MoviesDto> = arrayListOf()
    var itemClick: (Int) -> Unit = {}

    class CategoryViewHolder(private var binding: MoviesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviesDto) {
            with(binding) {

                tvMovieName.text = item.title

                item.voteAverage.let {
                    rbRate.rating = it!!.toFloat() / 2
                }

                val genres = mutableListOf<String>()
                for ((id, genre) in Constants.genreMap) {
                    if (item.genreIds.contains(id)) {
                        genres.add(genre)
                    }
                }
                tvMovieCategories.text = genres.joinToString(", ")

                item.posterPath.let {
                    binding.root.context.imageInto(it!!, iwMovie)
                }


            }
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