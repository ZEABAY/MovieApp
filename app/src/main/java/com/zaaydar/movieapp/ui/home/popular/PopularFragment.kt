package com.zaaydar.movieapp.ui.home.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.FragmentPopularBinding
import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.util.Constants.genreMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment() {
    private lateinit var binding: FragmentPopularBinding

    private lateinit var popularViewModel: PopularViewModel
    private var popularAdapter = PopularAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularViewModel = ViewModelProvider(this)[PopularViewModel::class.java]
        popularViewModel.getPopularMoviesFromApi()
        popularViewModel.getGenresFromApi()

        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = popularAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        popularViewModel.getNextPagePopularMoviesFromApi()
                    }
                }
            })

        }

        observeLiveData()
    }

    private fun observeLiveData() {
        popularViewModel.popularMovies.observe(viewLifecycleOwner) { populars ->
            populars?.let {
                binding.apply {
                    rvPopular.visibility = View.VISIBLE
                    pbLoading.visibility = View.GONE
                    pbLoadingNext.visibility = View.GONE
                    popularAdapter.updatePopularList(populars)
                }
            }
        }

        popularViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        popularViewModel.loading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                binding.apply {
                    if (loading) {
                        tvError.visibility = View.GONE
                        rvPopular.visibility = View.GONE
                        pbLoading.visibility = View.VISIBLE
                    } else {
                        tvError.visibility = View.GONE
                    }
                }
            }
        }

        popularViewModel.loadingNext.observe(viewLifecycleOwner) { loadingNext ->
            loadingNext?.let {
                binding.apply {
                    if (loadingNext) {
                        tvError.visibility = View.GONE
                        rvPopular.visibility = View.GONE
                        pbLoadingNext.visibility = View.VISIBLE
                    } else {
                        tvError.visibility = View.GONE
                    }
                }
            }
        }

        popularViewModel.movieGenres.observe(viewLifecycleOwner) { movieGenre ->
            movieGenre?.let {
                for (item: MovieGenre.Genre in movieGenre.genres) {
                    genreMap[item.id] = item.name
                }
            }
        }
    }

}