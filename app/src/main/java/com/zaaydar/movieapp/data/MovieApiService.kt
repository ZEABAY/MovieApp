package com.zaaydar.movieapp.data

import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.PopularMoviesResponse
import io.reactivex.Single
import javax.inject.Inject

class MovieApiService @Inject constructor(
    private val api: MovieApiInterface
) {

    fun getPopulars(): Single<PopularMoviesResponse> {
        return api.getPopularMovies()
    }

    fun getGenres(): Single<MovieGenre> {
        return api.getMovieGenres()
    }
}