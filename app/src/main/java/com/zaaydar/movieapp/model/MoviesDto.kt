package com.zaaydar.movieapp.model

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("genre_ids")
    val genreStrings: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("is_favorite")
    var isFavorite: Boolean = false
)


