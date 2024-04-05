package com.zaaydar.movieapp.data

import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.PopularMoviesResponse
import com.zaaydar.movieapp.util.Constants.API_KEY
import com.zaaydar.movieapp.util.Constants.GENRE
import com.zaaydar.movieapp.util.Constants.LANG_EN
import com.zaaydar.movieapp.util.Constants.LIST
import com.zaaydar.movieapp.util.Constants.MOVIE
import com.zaaydar.movieapp.util.Constants.POPULAR
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {

    @GET(MOVIE + POPULAR)
    fun getPopularMovies(
        @Query("page") page: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<PopularMoviesResponse>

    @GET(GENRE + MOVIE + LIST)
    fun getMovieGenres(
        @Query("language") language: String = LANG_EN,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<MovieGenre>

}