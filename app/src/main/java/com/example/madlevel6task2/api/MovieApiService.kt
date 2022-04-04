package com.example.madlevel6task2.api

import com.example.madlevel6task2.model.MovieItem
import retrofit2.http.GET

interface MovieApiService {

    //The GET method needed to retrieve movies
    @GET("/discover/movie?primary_release_year=%d")
    suspend fun getMoviesForYear(year: Int): List<MovieItem>
}