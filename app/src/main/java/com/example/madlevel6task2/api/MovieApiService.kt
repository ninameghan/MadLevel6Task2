package com.example.madlevel6task2.api

import com.example.madlevel6task2.model.MovieItem
import com.example.madlevel6task2.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    //The GET method needed to retrieve movies
    @GET("movie?api_key=53923694b94fba1187f4e34cd718f503&langiage=en-US&sort_by=popularity.desc&with_original_language=en")
    suspend fun getMoviesForYear(@Query("year") year: Int): MovieList
}