package com.zaaydar.movieapp.data.repository

import com.zaaydar.movieapp.data.remote.MovieApiInterface
import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.SearchResultResponse
import com.zaaydar.movieapp.model.category.CategoryResponse
import com.zaaydar.movieapp.model.moviedetail.MovieDetailResponse
import com.zaaydar.movieapp.model.nowplaying.NowPlayingResponse
import com.zaaydar.movieapp.model.popular.PopularMoviesResponse
import com.zaaydar.movieapp.model.toprated.TopRatedResponse
import com.zaaydar.movieapp.model.upcoming.UpcomingResponse
import io.reactivex.Single
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApiInterface
) {
    fun getPopulars(page: Int): Single<PopularMoviesResponse> {
        return api.getPopularMovies(page)
    }

    fun getNowPlayings(page: Int): Single<NowPlayingResponse> {
        return api.getNowPlayingsMovies(page)
    }

    fun getTopRated(page: Int): Single<TopRatedResponse> {
        return api.getTopRatedMovies(page)
    }

    fun getUpcoming(page: Int): Single<UpcomingResponse> {
        return api.getUpcomingMovies(page)
    }

    fun getMoviesByCategory(genre: Int, page: Int): Single<CategoryResponse> {
        return api.getMoviesByCategory(genre, page)
    }

    fun getGenres(): Single<MovieGenre> {
        return api.getMovieGenres()
    }

    fun getDetails(id: Int): Single<MovieDetailResponse> {
        return api.getMovieById(id)
    }

    fun getSearch(page: Int, searchText: String): Single<SearchResultResponse> {
        return api.searchMovie(page, searchText)
    }
}