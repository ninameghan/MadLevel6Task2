package com.example.madlevel6task2.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.madlevel6task2.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application){

    private val movieRepository = MovieRepository()

    /**
     * This property points direct to the LiveData in the repository, that value
     * gets updated when the use clicks the submit button.
     */
    val movies = movieRepository.movies
    var movie: MovieItem? = null

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * errortext can be observed from the Activity for error showing
     */
    val errorText: LiveData<String>
    get() = _errorText

    /**The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the
     * ViewModel is cleared.
     * Extension method of lifecycle-viewmodel-ktx library
     */
    fun getMoviesForYear(year: String) {
        viewModelScope.launch {
            try {
                //The movieRepository sets its own livedata property
                //our own movies property points to this one
                movieRepository.getMoviesForYear(year.toInt())
            } catch (error: MovieRepository.MovieError) {
                _errorText.value = error.message
                Log.e("Movie error", error.cause.toString())
            }

        }
    }


}