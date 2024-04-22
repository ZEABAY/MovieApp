package com.zaaydar.movieapp.data.repository

import com.zaaydar.movieapp.data.remote.MovieApiInterface
import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.model.category.CategoryResponse
import com.zaaydar.movieapp.model.moviedetail.MovieDetailResponse
import com.zaaydar.movieapp.model.searchresult.SearchResultResponse
import com.zaaydar.movieapp.util.toMoviesDto
import io.reactivex.Single
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApiInterface
) {
    fun getPopulars(page: Int): Single<List<MoviesDto>> {
        return api.getPopularMovies(page).map { it.toMoviesDto() }
    }

    fun getNowPlayings(page: Int): Single<List<MoviesDto>> {
        return api.getNowPlayingsMovies(page).map { it.toMoviesDto() }
    }


    fun getTopRated(page: Int): Single<List<MoviesDto>> {
        return api.getTopRatedMovies(page).map { it.toMoviesDto() }
    }

    fun getUpcoming(page: Int): Single<List<MoviesDto>> {
        return api.getUpcomingMovies(page).map { it.toMoviesDto() }
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

    fun getMovieTrailer(movieId: Int): Single<String> {

        return api.getMovieTrailer(movieId).map {
            it.results[0].key
        }
    }
}