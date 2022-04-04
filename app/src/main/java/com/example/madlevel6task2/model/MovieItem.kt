package com.example.madlevel6task2.model

data class MovieItem(
    val title: String,
    val releaseDate: String,
    val rating: Double,
    val overview: String
) {
    fun getImageUrl() = ""
}
