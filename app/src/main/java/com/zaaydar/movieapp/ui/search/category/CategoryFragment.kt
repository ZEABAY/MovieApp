package com.zaaydar.movieapp.ui.search.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding

    private lateinit var categoryViewModel: CategoryViewModel
    private var categoryAdapter = CategoryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genre = arguments?.getInt("genre")
        if (genre != null) {
            categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
            categoryViewModel.getMoviesByCategoryFromApi(genre)
        } else {
            // Eğer genre null ise, hata durumunu yönetebilirsiniz
            Log.e("SearchFragment", "Genre argument is null")
        }

        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1) && genre != null) {
                        categoryViewModel.getNextPageCategoryFromApi(genre)
                    }
                }
            })

        }

        observeLiveData()
    }

    private fun observeLiveData() {
        categoryViewModel.categoryMovies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                binding.apply {
                    rvCategory.visibility = View.VISIBLE
                    pbCategoryLoading.visibility = View.GONE
                    pbCategoryLoadingNext.visibility = View.GONE
                    categoryAdapter.updateCategoryList(movies)
                }
            }
        }

        categoryViewModel.categoryError.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvCategoryError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        categoryViewModel.categoryLoading.observe(viewLifecycleOwner) { loading ->
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

        categoryViewModel.categoryLoadingNext.observe(viewLifecycleOwner) { loadingNext ->
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