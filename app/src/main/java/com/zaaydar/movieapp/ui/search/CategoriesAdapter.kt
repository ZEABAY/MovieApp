package com.zaaydar.movieapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.R
import com.zaaydar.movieapp.databinding.CategoriesRowBinding
import com.zaaydar.movieapp.ui.search.category.CategoryFragment
import com.zaaydar.movieapp.util.Constants

class CategoriesAdapter(private val fragmentManager: FragmentManager) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var genreMap = Constants.genreMap

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
            //println(position.toString() + " " + genre + " " + genreMap[genre])
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putInt("genre", genre)
            fragment.arguments = bundle
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment)
                .addToBackStack(null).commit()
        }
    }


}