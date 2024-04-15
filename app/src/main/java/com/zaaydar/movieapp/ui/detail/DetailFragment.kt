package com.zaaydar.movieapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zaaydar.movieapp.databinding.FragmentDetailBinding
import com.zaaydar.movieapp.util.imageInto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    private var movieId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            movieId = DetailFragmentArgs.fromBundle(it).id
        }

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getMovieDetailById(movieId)




        observeLiveData()
    }

    private fun observeLiveData() {

        detailViewModel.movieDetail.observe(viewLifecycleOwner) { details ->
            details?.let {
                binding.apply {

                    ivBack.setOnClickListener {
                        findNavController().popBackStack()
                    }

                    pbDetailLoading.visibility = View.GONE
                    tvDetailError.visibility = View.GONE

                    binding.root.context.imageInto(details.posterPath, iwMovie)
                    tvMovieName.text = details.title
                    tvTagline.text = details.tagline
                    tvReleaseDate.text = details.releaseDate
                    tvRuntime.text = minToHours(details.runtime)
                    tvGenres.text = details.genres.joinToString(", ")
                    tvLanguage.text = details.spokenLanguage
                    tvRating.text = (details.voteAverage / 2).toString()
                    tvReviews.text = details.voteCount.toString()
                    tvOverview.text = details.overview


                }
            }
        }

        detailViewModel.detailError.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.apply {
                    tvDetailError.visibility = if (error) View.VISIBLE else View.GONE
                }
            }
        }

        detailViewModel.detailLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                binding.apply {
                    if (loading) {
                        tvDetailError.visibility = View.GONE
                        pbDetailLoading.visibility = View.VISIBLE
                    } else {
                        tvDetailError.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun minToHours(min: Int): String {
        val hours = min / 60
        val minLeft = min % 60
        return String.format("%02d:%02d", hours, minLeft)
    }
}