package com.example.madlevel6task2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel6task2.api.MovieApi
import com.example.madlevel6task2.api.MovieApiService
import com.example.madlevel6task2.model.MovieItem
import kotlinx.coroutines.withTimeout

class MovieRepository {

    private val movieApiService: MovieApiService = MovieApi.createApi()
    private val _movies: MutableLiveData<List<MovieItem>> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     */
    val movies: LiveData<List<MovieItem>>
    get() = _movies

    /**
     * Suspend fucntion that calls a suspend fucntion from the moviesApi call
     */
    suspend fun getMoviesForYear(year: Int) {
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(5_000) {
                movieApiService.getMoviesForYear(year)
            }
            _movies.value = result
        } catch (error: Throwable) {
            throw MovieError("Unable to find movies for this year", error)
        }
    }

    class MovieError(message: String, cause: Throwable): Throwable(message, cause)
}