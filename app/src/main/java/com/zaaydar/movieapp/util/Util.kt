package com.zaaydar.movieapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zaaydar.movieapp.model.MovieDetailDto
import com.zaaydar.movieapp.model.MoviesDto
import com.zaaydar.movieapp.model.category.CategoryResponse
import com.zaaydar.movieapp.model.moviedetail.MovieDetailResponse
import com.zaaydar.movieapp.model.nowplaying.NowPlayingResponse
import com.zaaydar.movieapp.model.popular.PopularMoviesResponse
import com.zaaydar.movieapp.model.toprated.TopRatedResponse
import com.zaaydar.movieapp.model.upcoming.UpcomingResponse


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

fun CategoryResponse.toMoviesDto(): List<MoviesDto> {
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

fun UpcomingResponse.toMoviesDto(): List<MoviesDto> {
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


fun MovieDetailResponse.toDetailsDto(): MovieDetailDto {
    val genres = arrayListOf<String>()
    for (item in this.genres) {
        genres.add(item.name)
    }

    return MovieDetailDto(
        genres,
        id,
        overview,
        posterPath,
        releaseDate,
        runtime,
        spokenLanguages[0].englishName,
        tagline,
        title,
        voteAverage,
        voteCount

    )
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

