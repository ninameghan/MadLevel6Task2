package com.example.madlevel6task2.model

import com.google.gson.annotations.SerializedName

data class MovieItem(

    @SerializedName("title")
    val title: String,
    @SerializedName("releaseDate")
    val releaseDate: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("moviePoster")
    val moviePoster: String,
    @SerializedName("movieBackdrop")
    val movieBackdrop: String
) {
    fun getPosterImageUrl() = "https://image.tmdb.org/t/p/w200$moviePoster"

    fun getBackdropImageUrl() = "https://image.tmdb.org/t/p/w1000$movieBackdrop"
}
