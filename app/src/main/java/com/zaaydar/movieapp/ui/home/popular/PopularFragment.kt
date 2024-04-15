package com.zaaydar.movieapp.ui.home.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zaaydar.movieapp.databinding.FragmentPopularBinding
import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.ui.home.HomeFragmentDirections
import com.zaaydar.movieapp.util.Constants.genreMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment() {
    private lateinit var binding: FragmentPopularBinding

    private lateinit var popularViewModel: PopularViewModel
    private val popularAdapter by lazy { PopularAdapter() }

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

            adapter = popularAdapter

            popularAdapter.itemClick = {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }

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
        popularViewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                binding.apply {
                    rvPopular.visibility = View.VISIBLE
                    pbPopularLoading.visibility = View.GONE
                    pbPopularLoadingNext.visibility = View.GONE
                    popularAdapter.updatePopularList(movies)
                }
            }
        }

        popularViewModel.popularError.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvPopularError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        popularViewModel.popularLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                binding.apply {
                    if (loading) {
                        tvPopularError.visibility = View.GONE
                        rvPopular.visibility = View.GONE
                        pbPopularLoading.visibility = View.VISIBLE
                    } else {
                        tvPopularError.visibility = View.GONE
                    }
                }
            }
        }

        popularViewModel.popularLoadingNext.observe(viewLifecycleOwner) { loadingNext ->
            loadingNext?.let {
                binding.apply {
                    if (loadingNext) {
                        tvPopularError.visibility = View.GONE
                        rvPopular.visibility = View.VISIBLE
                        pbPopularLoadingNext.visibility = View.VISIBLE
                    } else {
                        tvPopularError.visibility = View.GONE
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPopular.adapter = null
    }

}