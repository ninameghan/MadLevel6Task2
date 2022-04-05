package com.example.madlevel6task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel6task2.R
import com.example.madlevel6task2.adapter.MovieAdapter
import com.example.madlevel6task2.databinding.FragmentOverviewBinding
import com.example.madlevel6task2.model.MovieItem
import com.example.madlevel6task2.model.MovieViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OverviewFragment : Fragment() {

    private val movies = arrayListOf<MovieItem>()
    private val viewModel: MovieViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    private var _binding: FragmentOverviewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        initRv()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter(movies, ::onMovieClick)

        binding.btnSubmit.setOnClickListener {
            val year = binding.etYear.text.toString()
            viewModel.getMoviesForYear(year)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRv() {
        binding.rvMovies.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.rvMovies.adapter = movieAdapter
        observeMovies()
    }

    private fun onMovieClick(movieItem: MovieItem) {
        viewModel.movie = movieItem
        findNavController().navigate(R.id.action_OverviewFragment_to_MovieFragment)
    }

    private fun observeMovies() {
        viewModel.movies.observe(viewLifecycleOwner) {
            movies.clear()
            movies.addAll(it)
            movieAdapter.notifyDataSetChanged()
        }
        viewModel.errorText.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }
}