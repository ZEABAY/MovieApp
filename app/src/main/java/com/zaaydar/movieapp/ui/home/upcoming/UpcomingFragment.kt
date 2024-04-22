package com.zaaydar.movieapp.ui.home.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.FragmentUpcomingBinding
import com.zaaydar.movieapp.ui.adapters.MovieListsAdapter
import com.zaaydar.movieapp.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingBinding

    private lateinit var upcomingViewModel: UpcomingViewModel

    private val upcomingAdapter by lazy { MovieListsAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingViewModel = ViewModelProvider(this)[UpcomingViewModel::class.java]
        upcomingViewModel.getUpcomingMoviesFromApi()

        binding.rvUpcoming.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = upcomingAdapter

            upcomingAdapter.itemClick = {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        upcomingViewModel.getNextPageUpcomingMoviesFromApi()
                    }
                }
            })

        }

        observeLiveData()
    }

    private fun observeLiveData() {
        upcomingViewModel.upcomingMovies.observe(viewLifecycleOwner) { upcoming ->
            upcoming?.let {
                binding.apply {
                    rvUpcoming.visibility = View.VISIBLE
                    pbUpcomingLoading.visibility = View.GONE
                    pbUpcomingLoadingNext.visibility = View.GONE
                    upcomingAdapter.updateMoviesList(upcoming)
                }
            }
        }

        upcomingViewModel.upcomingError.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvUpcomingError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        upcomingViewModel.upcomingLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                binding.apply {
                    if (loading) {
                        tvUpcomingError.visibility = View.GONE
                        rvUpcoming.visibility = View.GONE
                        pbUpcomingLoading.visibility = View.VISIBLE
                    } else {
                        tvUpcomingError.visibility = View.GONE
                    }
                }
            }
        }

        upcomingViewModel.upcomingLoadingNext.observe(viewLifecycleOwner) { loadingNext ->
            loadingNext?.let {
                binding.apply {
                    if (loadingNext) {
                        tvUpcomingError.visibility = View.GONE
                        rvUpcoming.visibility = View.VISIBLE
                        pbUpcomingLoadingNext.visibility = View.VISIBLE
                    } else {
                        tvUpcomingError.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvUpcoming.adapter = null
    }


    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)

        if (menuVisible) {
            upcomingAdapter.refresh()
        }
    }
}