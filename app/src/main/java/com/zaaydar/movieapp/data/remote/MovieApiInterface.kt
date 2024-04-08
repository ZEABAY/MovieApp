package com.zaaydar.movieapp.data.remote

import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.nowplaying.NowPlayingResponse
import com.zaaydar.movieapp.model.popular.PopularMoviesResponse
import com.zaaydar.movieapp.model.toprated.TopRatedResponse
import com.zaaydar.movieapp.util.Constants.API_KEY
import com.zaaydar.movieapp.util.Constants.GENRE
import com.zaaydar.movieapp.util.Constants.LANG_EN
import com.zaaydar.movieapp.util.Constants.LIST
import com.zaaydar.movieapp.util.Constants.MOVIE
import com.zaaydar.movieapp.util.Constants.NOW_PLAYING
import com.zaaydar.movieapp.util.Constants.POPULAR
import com.zaaydar.movieapp.util.Constants.TOP_RATED
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {

    @GET(MOVIE + POPULAR)
    fun getPopularMovies(
        @Query("page") page: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<PopularMoviesResponse>


    @GET(MOVIE + NOW_PLAYING)
    fun getNowPlayingsMovies(
        @Query("page") page: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<NowPlayingResponse>

    @GET(MOVIE + TOP_RATED)
    fun getTopRatedMovies(
        @Query("page") page: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<TopRatedResponse>

    @GET(GENRE + MOVIE + LIST)
    fun getMovieGenres(
        @Query("language") language: String = LANG_EN,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<MovieGenre>

}