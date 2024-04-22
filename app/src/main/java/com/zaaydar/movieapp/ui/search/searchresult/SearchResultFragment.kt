package com.zaaydar.movieapp.ui.search.searchresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.FragmentSearchResultBinding
import com.zaaydar.movieapp.ui.adapters.MovieListsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var searchResultViewModel: SearchResultViewModel
    private val searchResultAdapter by lazy { MovieListsAdapter() }

    private var searchText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            searchText = SearchResultFragmentArgs.fromBundle(it).searchText
        }
        searchResultViewModel = ViewModelProvider(this)[SearchResultViewModel::class.java]
        searchResultViewModel.getSearchResultMoviesFromApi(searchText)

        binding.apply {
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }

            tvSearchText.text = searchText
        }


        binding.rvSearchResult.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchResultAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        searchResultViewModel.getNextSearchResultMoviesFromApi(searchText)
                    }
                }
            })

            searchResultAdapter.itemClick = {
                val action =
                    SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }

        }

        observeLiveData()

    }

    private fun observeLiveData() {
        searchResultViewModel.searchResultMovies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                binding.apply {
                    rvSearchResult.visibility = View.VISIBLE
                    pbSearchResultLoading.visibility = View.GONE
                    searchResultAdapter.updateMoviesList(movies)
                }
            }
        }

        searchResultViewModel.searchResultError.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvSearchResultError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        searchResultViewModel.searchResultLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                binding.apply {
                    if (loading) {
                        tvSearchResultError.visibility = View.GONE
                        rvSearchResult.visibility = View.GONE
                        pbSearchResultLoading.visibility = View.VISIBLE
                    } else {
                        tvSearchResultError.visibility = View.GONE
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvSearchResult.adapter = null
    }
}