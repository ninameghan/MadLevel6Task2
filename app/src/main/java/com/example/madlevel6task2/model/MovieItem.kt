package com.example.madlevel6task2.model

import com.google.gson.annotations.SerializedName

data class MovieItem(

    @SerializedName("title")
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val moviePoster: String,
    @SerializedName("backdrop_path")
    val movieBackdrop: String
) {
    fun getPosterImageUrl() = "https://image.tmdb.org/t/p/w200$moviePoster"

    fun getBackdropImageUrl() = "https://image.tmdb.org/t/p/w500$movieBackdrop"
}
