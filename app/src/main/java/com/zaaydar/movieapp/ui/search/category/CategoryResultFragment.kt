package com.zaaydar.movieapp.ui.search.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.FragmentCategoryBinding
import com.zaaydar.movieapp.ui.adapters.MovieListsAdapter
import com.zaaydar.movieapp.util.MySingleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryResultFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding

    private lateinit var categoryResultViewModel: CategoryResultViewModel
    private val categoryResultAdapter by lazy { MovieListsAdapter() }
    private var genre: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            genre = CategoryResultFragmentArgs.fromBundle(it).genre
        }

        binding.tvGenreName.text = MySingleton.genreMap[genre]

        if (genre != null) {
            categoryResultViewModel = ViewModelProvider(this)[CategoryResultViewModel::class.java]
            categoryResultViewModel.getMoviesByCategoryFromApi(genre!!)
        } else {
            Log.e("SearchFragment", "Genre argument is null")
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.rvCategory.apply {

            layoutManager = LinearLayoutManager(context)
            adapter = categoryResultAdapter

            categoryResultAdapter.itemClick = {
                val action = CategoryResultFragmentDirections.actionCategoryFragment2ToDetailFragment(it)
                findNavController().navigate(action)
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1) && genre != null) {
                        categoryResultViewModel.getNextPageCategoryFromApi(genre!!)
                    }
                }
            })

        }

        observeLiveData()
    }

    private fun observeLiveData() {
        categoryResultViewModel.categoryMovies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                binding.apply {
                    rvCategory.visibility = View.VISIBLE
                    pbCategoryLoading.visibility = View.GONE
                    pbCategoryLoadingNext.visibility = View.GONE
                    categoryResultAdapter.updateMoviesList(movies)
                }
            }
        }

        categoryResultViewModel.categoryError.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvCategoryError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        categoryResultViewModel.categoryLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                binding.apply {
                    if (loading) {
                        tvCategoryError.visibility = View.GONE
                        rvCategory.visibility = View.GONE
                        pbCategoryLoading.visibility = View.VISIBLE
                    } else {
                        tvCategoryError.visibility = View.GONE
                    }
                }
            }
        }

        categoryResultViewModel.categoryLoadingNext.observe(viewLifecycleOwner) { loadingNext ->
            loadingNext?.let {
                binding.apply {
                    if (loadingNext) {
                        tvCategoryError.visibility = View.GONE
                        rvCategory.visibility = View.VISIBLE
                        pbCategoryLoadingNext.visibility = View.VISIBLE
                    } else {
                        tvCategoryError.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvCategory.adapter = null
    }
}