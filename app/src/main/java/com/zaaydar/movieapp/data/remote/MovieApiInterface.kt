package com.zaaydar.movieapp.data.remote

import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.MovieVideosResponse
import com.zaaydar.movieapp.model.category.CategoryResponse
import com.zaaydar.movieapp.model.moviedetail.MovieDetailResponse
import com.zaaydar.movieapp.model.nowplaying.NowPlayingResponse
import com.zaaydar.movieapp.model.popular.PopularMoviesResponse
import com.zaaydar.movieapp.model.searchresult.SearchResultResponse
import com.zaaydar.movieapp.model.toprated.TopRatedResponse
import com.zaaydar.movieapp.model.upcoming.UpcomingResponse
import com.zaaydar.movieapp.util.Constants.API_KEY
import com.zaaydar.movieapp.util.Constants.DISCOVER_MOVIE
import com.zaaydar.movieapp.util.Constants.GENRE
import com.zaaydar.movieapp.util.Constants.LANG_EN
import com.zaaydar.movieapp.util.Constants.LIST
import com.zaaydar.movieapp.util.Constants.MOVIE
import com.zaaydar.movieapp.util.Constants.NOW_PLAYING
import com.zaaydar.movieapp.util.Constants.POPULAR
import com.zaaydar.movieapp.util.Constants.SEARCH_MOVIE
import com.zaaydar.movieapp.util.Constants.TOP_RATED
import com.zaaydar.movieapp.util.Constants.UPCOMING
import com.zaaydar.movieapp.util.Constants.VIDEOS
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiInterface {

    @GET(MOVIE + POPULAR)
    fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<PopularMoviesResponse>


    @GET(MOVIE + NOW_PLAYING)
    fun getNowPlayingsMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<NowPlayingResponse>

    @GET(MOVIE + TOP_RATED)
    fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<TopRatedResponse>

    @GET(MOVIE + UPCOMING)
    fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<UpcomingResponse>

    @GET(DISCOVER_MOVIE)
    fun getMoviesByCategory(
        @Query("with_genres") genre: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<CategoryResponse>

    @GET(GENRE + MOVIE + LIST)
    fun getMovieGenres(
        @Query("language") language: String = LANG_EN,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<MovieGenre>

    @GET("$MOVIE{movie_id}")
    fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<MovieDetailResponse>

    @GET(SEARCH_MOVIE)
    fun searchMovie(
        @Query("page") page: Int,
        @Query("query") searchText: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<SearchResultResponse>

    @GET("$MOVIE{movie_id}$VIDEOS")
    fun getMovieTrailer(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Single<MovieVideosResponse>
}