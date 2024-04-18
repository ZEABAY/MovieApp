package com.zaaydar.movieapp.ui.profile.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zaaydar.movieapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding

    private lateinit var favoritesViewModel: FavoritesViewModel
    private val favoritesAdapter by lazy { FavoritesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        favoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        favoritesViewModel.getFavoriteMoviesFromApi()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.rvFavorites.apply {
            adapter = favoritesAdapter

            favoritesAdapter.itemClick = {
                val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }
        }

        observeLiveData()

    }

    private fun observeLiveData() {
        favoritesViewModel.favoritesMovies.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                binding.apply {
                    rvFavorites.visibility = View.VISIBLE
                    pbFavoritesLoading.visibility = View.GONE
                    favoritesAdapter.updateFavoriteMoviesList(movies)
                }
            }
        }

        favoritesViewModel.favoritesError.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvFavoritesError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        favoritesViewModel.favoritesLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                binding.apply {
                    if (loading) {
                        tvFavoritesError.visibility = View.GONE
                        rvFavorites.visibility = View.GONE
                        pbFavoritesLoading.visibility = View.VISIBLE
                    } else {
                        tvFavoritesError.visibility = View.GONE
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavorites.adapter = null
    }
}