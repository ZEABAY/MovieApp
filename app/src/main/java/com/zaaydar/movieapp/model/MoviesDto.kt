package com.zaaydar.movieapp.model

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
)