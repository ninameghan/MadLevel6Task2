package com.example.madlevel6task2.model

import com.google.gson.annotations.SerializedName

data class MovieList(

    @SerializedName("page")
    val page: Int,
    @SerializedName("movies")
    val movies: List<MovieItem>,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_movies")
    val totalMovies: Int
)
