package com.zaaydar.movieapp.util

object Constants {

    ///genre/movie/list?api_key=e14ee303e1373d68f64459635c44e339&language=en-US
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/original/"
    const val DISCOVER = "discover/"
    const val MOVIE = "movie/"
    const val POPULAR = "popular"
    const val CHANGES = "changes/"
    const val API_KEY = "e14ee303e1373d68f64459635c44e339"
    const val GENRE = "genre/"
    const val LIST = "list"
    const val LANG_EN = "en-US"
    const val LANG_TR = "tr-TR"

    val genreMap = mutableMapOf<Int, String>()
}