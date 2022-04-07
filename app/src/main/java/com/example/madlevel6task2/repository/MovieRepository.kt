package com.example.madlevel6task2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel6task2.api.MovieApi
import com.example.madlevel6task2.api.MovieApiService
import com.example.madlevel6task2.model.MovieItem
import com.example.madlevel6task2.model.MovieList
import kotlinx.coroutines.withTimeout

class MovieRepository {

    private val movieApiService: MovieApiService = MovieApi.createApi()
    val movies: MutableLiveData<List<MovieItem>> = MutableLiveData()
    var movie: MutableLiveData<MovieItem> = MutableLiveData()

    /**
     * Suspend fucntion that calls a suspend fucntion from the moviesApi call
     */
    suspend fun getMoviesForYear(year: Int) {
        try {
            //timeout the request after 5 seconds
            val result: MovieList = withTimeout(5_000) {
                movieApiService.getMoviesForYear(year)
            }
            movies.value = result.results
        } catch (error: Throwable) {
            throw MovieError("Unable to find movies for this year", error)
        }
    }

    class MovieError(message: String, cause: Throwable) : Throwable(message, cause)
}