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
import com.zaaydar.movieapp.model.searchresult.SearchResultResponse
import com.zaaydar.movieapp.model.toprated.TopRatedResponse
import com.zaaydar.movieapp.model.upcoming.UpcomingResponse
import com.zaaydar.movieapp.util.Constants.favorites


fun PopularMoviesResponse.toMoviesDto(): List<MoviesDto> {
    val list = arrayListOf<MoviesDto>()
    val favoriteSet = favorites

    for (item in results) {

        val voteAverage = item.voteAverage?.let {
            it.toFloat() / 2
        } ?: 0f

        val genres = arrayListOf<String>()
        for ((id, genre) in Constants.genreMap) {
            if (item.genreIds.contains(id)) {
                genres.add(genre)
            }
        }
        val genreString = genres.joinToString(", ")

        val dto = MoviesDto(
            item.id,
            item.title,
            genreString,
            voteAverage,
            item.posterPath,
            favoriteSet.contains(item.id.toLong())
        )

        list.add(dto)
    }
    return list
}

fun NowPlayingResponse.toMoviesDto(): List<MoviesDto> {
    val list = arrayListOf<MoviesDto>()
    val favoriteSet = favorites

    for (item in results) {

        val voteAverage = item.voteAverage?.let {
            it.toFloat() / 2
        } ?: 0f

        val genres = arrayListOf<String>()
        for ((id, genre) in Constants.genreMap) {
            if (item.genreIds.contains(id)) {
                genres.add(genre)
            }
        }
        val genreString = genres.joinToString(", ")

        val dto = MoviesDto(
            item.id,
            item.title,
            genreString,
            voteAverage,
            item.posterPath,
            favoriteSet.contains(item.id.toLong())
        )

        list.add(dto)
    }
    return list
}

fun CategoryResponse.toMoviesDto(): List<MoviesDto> {
    val list = arrayListOf<MoviesDto>()
    val favoriteSet = favorites

    for (item in results) {

        val voteAverage = item.voteAverage?.let {
            it.toFloat() / 2
        } ?: 0f

        val genres = arrayListOf<String>()
        for ((id, genre) in Constants.genreMap) {
            if (item.genreIds.contains(id)) {
                genres.add(genre)
            }
        }
        val genreString = genres.joinToString(", ")

        val dto = MoviesDto(
            item.id,
            item.title,
            genreString,
            voteAverage,
            item.posterPath,
            favoriteSet.contains(item.id.toLong())
        )

        list.add(dto)
    }

    return list
}

fun TopRatedResponse.toMoviesDto(): List<MoviesDto> {
    val list = arrayListOf<MoviesDto>()
    val favoriteSet = favorites

    for (item in results) {

        val voteAverage = item.voteAverage?.let {
            it.toFloat() / 2
        } ?: 0f

        val genres = arrayListOf<String>()
        for ((id, genre) in Constants.genreMap) {
            if (item.genreIds.contains(id)) {
                genres.add(genre)
            }
        }
        val genreString = genres.joinToString(", ")

        val dto = MoviesDto(
            item.id,
            item.title,
            genreString,
            voteAverage,
            item.posterPath,
            favoriteSet.contains(item.id.toLong())
        )

        list.add(dto)
    }
    return list
}

fun UpcomingResponse.toMoviesDto(): List<MoviesDto> {
    val list = arrayListOf<MoviesDto>()
    val favoriteSet = favorites

    for (item in results) {

        val voteAverage = item.voteAverage?.let {
            it.toFloat() / 2
        } ?: 0f

        val genres = arrayListOf<String>()
        for ((id, genre) in Constants.genreMap) {
            if (item.genreIds.contains(id)) {
                genres.add(genre)
            }
        }
        val genreString = genres.joinToString(", ")

        val dto = MoviesDto(
            item.id,
            item.title,
            genreString,
            voteAverage,
            item.posterPath,
            favoriteSet.contains(item.id.toLong())
        )

        list.add(dto)
    }
    return list
}

fun SearchResultResponse.toMoviesDto(): List<MoviesDto> {
    val list = arrayListOf<MoviesDto>()
    val favoriteSet = favorites

    for (item in results) {

        val voteAverage = item.voteAverage?.let {
            it.toFloat() / 2
        } ?: 0f

        val genres = arrayListOf<String>()
        for ((id, genre) in Constants.genreMap) {
            if (item.genreIds.contains(id)) {
                genres.add(genre)
            }
        }
        val genreString = genres.joinToString(", ")

        val dto = MoviesDto(
            item.id,
            item.title,
            genreString,
            voteAverage,
            item.posterPath,
            favoriteSet.contains(item.id.toLong())
        )

        list.add(dto)
    }

    println(list.toString())
    return list
}

fun MoviesDto.checkIsFav() {
    val favoriteSet = favorites
    isFavorite = favoriteSet.contains(id.toLong())
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
        voteCount,
    )
}

fun MovieDetailResponse.toMovieDto(): MoviesDto {
    val favoriteSet = favorites

    val genres = arrayListOf<String>()
    for (item in this.genres) {
        genres.add(item.name)
    }
    val genreString = genres.joinToString(", ")


    val voteAverage = voteAverage?.let {
        it.toFloat() / 2
    } ?: 0f

    return MoviesDto(
        id,
        title,
        genreString,
        voteAverage,
        posterPath,
        favoriteSet.contains(id.toLong())
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

