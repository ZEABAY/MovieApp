package com.zaaydar.movieapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailDto(
    @SerializedName("genres")
    val genres: List<String>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguage: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) : Parcelable {
    @Parcelize
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    ) : Parcelable

}