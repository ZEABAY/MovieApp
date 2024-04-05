package com.zaaydar.movieapp.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class MovieGenre(
    @SerializedName("genres")
    val genres: List<Genre>
) : Parcelable {
    @Parcelize
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    ) : Parcelable
}