package com.zaaydar.movieapp.util

object MySingleton {
    val genreMap = mutableMapOf<Int, String>()
    var userUUID: String = ""
    var favorites = arrayListOf<Long>()

}