package com.zaaydar.movieapp.ui.home.nowplaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.FragmentNowPlayingBinding
import com.zaaydar.movieapp.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment : Fragment() {

    private lateinit var binding: FragmentNowPlayingBinding

    private lateinit var nowPlayingViewModel: NowPlayingViewModel

    private val nowPlayingAdapter by lazy { NowPlayingAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNowPlayingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nowPlayingViewModel = ViewModelProvider(this)[NowPlayingViewModel::class.java]
        nowPlayingViewModel.getNowPlayingMoviesFromApi()

        binding.rvNowPlaying.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = nowPlayingAdapter


            nowPlayingAdapter.itemClick = {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        nowPlayingViewModel.getNextPageNowPlayingMoviesFromApi()
                    }
                }
            })

        }

        observeLiveData()
    }

    private fun observeLiveData() {
        nowPlayingViewModel.nowPlayingMovies.observe(viewLifecycleOwner) { nowPlaying ->
            nowPlaying?.let {
                binding.apply {
                    rvNowPlaying.visibility = View.VISIBLE
                    pbNowPlayingLoading.visibility = View.GONE
                    pbNowPlayingLoadingNext.visibility = View.GONE
                    nowPlayingAdapter.updateNowPlayingMoviesList(nowPlaying)
                }
            }
        }

        nowPlayingViewModel.nowPlayingError.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvNowPlayingError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        nowPlayingViewModel.nowPlayingLoading.observe(viewLifecycleOwner) { loading ->
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

        nowPlayingViewModel.nowPlayingLoadingNext.observe(viewLifecycleOwner) { loadingNext ->
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