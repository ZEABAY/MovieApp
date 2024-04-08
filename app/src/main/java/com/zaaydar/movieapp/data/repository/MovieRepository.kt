package com.zaaydar.movieapp.data.repository

import com.zaaydar.movieapp.data.remote.MovieApiInterface
import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.nowplaying.NowPlayingResponse
import com.zaaydar.movieapp.model.popular.PopularMoviesResponse
import com.zaaydar.movieapp.model.toprated.TopRatedResponse
import io.reactivex.Single
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApiInterface
) {
    fun getPopulars(page: String): Single<PopularMoviesResponse> {
        return api.getPopularMovies(page)
    }

    fun getNowPlayings(page: String): Single<NowPlayingResponse> {
        return api.getNowPlayingsMovies(page)
    }

    fun getTopRated(page: String): Single<TopRatedResponse> {
        return api.getTopRatedMovies(page)
    }

    fun getGenres(): Single<MovieGenre> {
        return api.getMovieGenres()
    }
}