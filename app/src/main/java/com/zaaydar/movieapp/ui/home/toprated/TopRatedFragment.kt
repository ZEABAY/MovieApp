package com.zaaydar.movieapp.ui.home.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.FragmentNowPlayingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : Fragment() {

    private lateinit var binding: FragmentNowPlayingBinding

    private lateinit var topRatedViewModel: TopRatedViewModel
    private var topRatedAdapter = TopRatedAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNowPlayingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topRatedViewModel = ViewModelProvider(this)[TopRatedViewModel::class.java]
        topRatedViewModel.getTopRatedMoviesFromApi()

        binding.rvNowPlaying.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = topRatedAdapter

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
                    rvNowPlaying.visibility = View.VISIBLE
                    pbNowPlayingLoading.visibility = View.GONE
                    pbNowPlayingLoadingNext.visibility = View.GONE
                    topRatedAdapter.updateNowPlayingMoviesList(topRated)
                }
            }
        }

        topRatedViewModel.topRatedError.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvNowPlayingError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        topRatedViewModel.topRatedLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                binding.apply {
                    if (loading) {
                        tvNowPlayingError.visibility = View.GONE
                        rvNowPlaying.visibility = View.GONE
                        pbNowPlayingLoading.visibility = View.VISIBLE
                    } else {
                        tvNowPlayingError.visibility = View.GONE
                    }
                }
            }
        }

        topRatedViewModel.topRatedLoadingNext.observe(viewLifecycleOwner) { loadingNext ->
            loadingNext?.let {
                binding.apply {
                    if (loadingNext) {
                        tvNowPlayingError.visibility = View.GONE
                        rvNowPlaying.visibility = View.VISIBLE
                        pbNowPlayingLoadingNext.visibility = View.VISIBLE
                    } else {
                        tvNowPlayingError.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvNowPlaying.adapter = null
    }

}