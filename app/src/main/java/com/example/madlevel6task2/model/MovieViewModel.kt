package com.example.madlevel6task2.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.madlevel6task2.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application){

    private val movieRepository = MovieRepository()
    val selectedMovie = movieRepository.movie
    val movies = movieRepository.movies

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    val errorText: LiveData<String>
    get() = _errorText

    /**The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the
     * ViewModel is cleared.
     * Extension method of lifecycle-viewmodel-ktx library
     */
    fun getMoviesForYear(year: Int) {
        viewModelScope.launch {
            try {
                //The movieRepository sets its own livedata property
                //our own movies property points to this one
                movieRepository.getMoviesForYear(year)
            } catch (error: MovieRepository.MovieError) {
                _errorText.value = error.message
                Log.e("Movie error", error.cause.toString())
            }
        }
    }

    fun setMovie(newMovie: MovieItem) {
        viewModelScope.launch {
            selectedMovie.value = newMovie
        }
    }

}