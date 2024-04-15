package com.zaaydar.movieapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.CategoriesRowBinding
import com.zaaydar.movieapp.util.Constants

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var genreMap = Constants.genreMap
    var itemClick: (Int) -> Unit = {}


    class CategoriesViewHolder(private var binding: CategoriesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(key: Int, value: String) {
            with(binding) {
                tvCategoryName.text = value

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            CategoriesRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return genreMap.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val genre = genreMap.keys.toList()[position]
        val categoriesList = genreMap[genre]
        categoriesList?.let {
            holder.bind(
                genre,
                it
            )

        }

        holder.itemView.setOnClickListener {
            itemClick(genre)
        }
    }


}