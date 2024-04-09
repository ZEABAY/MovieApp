package com.zaaydar.movieapp.data.repository

import com.zaaydar.movieapp.data.remote.MovieApiInterface
import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.category.CategoryResponse
import com.zaaydar.movieapp.model.nowplaying.NowPlayingResponse
import com.zaaydar.movieapp.model.popular.PopularMoviesResponse
import com.zaaydar.movieapp.model.toprated.TopRatedResponse
import com.zaaydar.movieapp.model.upcoming.UpcomingResponse
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

    fun getUpcoming(page: String): Single<UpcomingResponse> {
        return api.getUpcomingMovies(page)
    }

    fun getMoviesByCategory(genre: Int, page: String): Single<CategoryResponse> {
        return api.getMoviesByCategory(genre, page)
    }

    fun getGenres(): Single<MovieGenre> {
        return api.getMovieGenres()
    }
}