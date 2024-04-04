package com.zaaydar.movieapp.service

import com.zaaydar.movieapp.model.PopularMoviesResponse
import com.zaaydar.movieapp.util.Constants.API_KEY
import com.zaaydar.movieapp.util.Constants.MOVIE
import com.zaaydar.movieapp.util.Constants.POPULAR
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {

    @GET(MOVIE + POPULAR)
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): Single<PopularMoviesResponse>


}
