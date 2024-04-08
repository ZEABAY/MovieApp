package com.zaaydar.movieapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.model.nowplaying.NowPlayingResponse
import com.zaaydar.movieapp.model.popular.PopularMoviesResponse
import com.zaaydar.movieapp.model.toprated.TopRatedResponse


fun PopularMoviesResponse.toMoviesDto(): List<MoviesDto> {
    val list = arrayListOf<MoviesDto>()

    for (item in results) {

        val dto = MoviesDto(
            item.id,
            item.title,
            item.genreIds,
            item.voteAverage,
            item.posterPath
        )

        list.add(dto)
    }
    return list
}

fun NowPlayingResponse.toMoviesDto(): List<MoviesDto> {
    val list = arrayListOf<MoviesDto>()

    for (item in results) {

        val dto = MoviesDto(
            item.id,
            item.title,
            item.genreIds,
            item.voteAverage,
            item.posterPath
        )

        list.add(dto)
    }
    return list
}

fun TopRatedResponse.toMoviesDto(): List<MoviesDto> {
    val list = arrayListOf<MoviesDto>()

    for (item in results) {

        val dto = MoviesDto(
            item.id,
            item.title,
            item.genreIds,
            item.voteAverage,
            item.posterPath
        )

        list.add(dto)
    }
    return list
}


fun Context.imageInto(url: String, into: ImageView) {
    val placeholder = CircularProgressDrawable(this).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
    val options = RequestOptions().placeholder(placeholder)
        .error(android.R.drawable.stat_notify_error)

    Glide.with(this)
        .setDefaultRequestOptions(options)
        .load(Constants.POSTER_BASE_URL + url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(into)
}

