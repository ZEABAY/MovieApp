package com.zaaydar.movieapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaaydar.movieapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var categoriesAdapter: CategoriesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FragmentManager'ı alıyoruz
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        // CategoriesAdapter'ı oluştururken FragmentManager'ı geçiyoruz
        categoriesAdapter = CategoriesAdapter(fragmentManager)

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoriesAdapter
        }
    }
}