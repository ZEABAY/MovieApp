package com.zaaydar.movieapp.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaaydar.movieapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val categoriesAdapter by lazy { CategoriesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvCategories.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = categoriesAdapter

                categoriesAdapter.itemClick = {
                    val action =
                        SearchFragmentDirections.actionSearchFragmentToCategoryFragment2(it)
                    findNavController().navigate(action)
                }
            }
            etSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER &&
                    etSearch.text.toString().isNotBlank()
                ) {
                    val action =
                        SearchFragmentDirections.actionSearchFragmentToSearchResultFragment2(
                            etSearch.text.toString()
                        )
                    findNavController().navigate(action)
                    true
                } else {
                    false
                }
            }


        }
    }
}