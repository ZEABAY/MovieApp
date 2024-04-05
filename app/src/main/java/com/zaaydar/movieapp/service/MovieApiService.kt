package com.zaaydar.movieapp.service

import com.zaaydar.movieapp.model.MovieGenre
import com.zaaydar.movieapp.model.PopularMoviesResponse
import com.zaaydar.movieapp.util.Constants.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiService {


    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieApiInterface::class.java)

    fun getPopulars(): Single<PopularMoviesResponse> {
        return api.getPopularMovies()
    }

    fun getGenres(): Single<MovieGenre> {
        return api.getMovieGenres()
    }
}