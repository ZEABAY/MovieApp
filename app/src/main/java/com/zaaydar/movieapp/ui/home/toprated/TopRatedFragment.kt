package com.zaaydar.movieapp.ui.home.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.FragmentTopRatedBinding
import com.zaaydar.movieapp.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : Fragment() {

    private lateinit var binding: FragmentTopRatedBinding

    private lateinit var topRatedViewModel: TopRatedViewModel

    private val topRatedAdapter by lazy { TopRatedAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopRatedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topRatedViewModel = ViewModelProvider(this)[TopRatedViewModel::class.java]
        topRatedViewModel.getTopRatedMoviesFromApi()

        binding.rvTopRated.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = topRatedAdapter

            topRatedAdapter.itemClick = {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        topRatedViewModel.getNextPageTopRatedMoviesFromApi()
                    }
                }
            })

        }

        observeLiveData()
    }

    private fun observeLiveData() {
        topRatedViewModel.topRatedMovies.observe(viewLifecycleOwner) { topRated ->
            topRated?.let {
                binding.apply {
                    rvTopRated.visibility = View.VISIBLE
                    pbTopRatedLoading.visibility = View.GONE
                    pbTopRatedLoadingNext.visibility = View.GONE
                    topRatedAdapter.updateTopRatedMoviesList(topRated)
                }
            }
        }

        topRatedViewModel.topRatedError.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvTopRatedError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        topRatedViewModel.topRatedLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                binding.apply {
                    if (loading) {
                        tvTopRatedError.visibility = View.GONE
                        rvTopRated.visibility = View.GONE
                        pbTopRatedLoading.visibility = View.VISIBLE
                    } else {
                        tvTopRatedError.visibility = View.GONE
                    }
                }
            }
        }

        topRatedViewModel.topRatedLoadingNext.observe(viewLifecycleOwner) { loadingNext ->
            loadingNext?.let {
                binding.apply {
                    if (loadingNext) {
                        tvTopRatedError.visibility = View.GONE
                        rvTopRated.visibility = View.VISIBLE
                        pbTopRatedLoadingNext.visibility = View.VISIBLE
                    } else {
                        tvTopRatedError.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvTopRated.adapter = null
    }


    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)

        if (menuVisible) {
            topRatedAdapter.refresh()
        }
    }
}