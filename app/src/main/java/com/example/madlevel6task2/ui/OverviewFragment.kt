package com.example.madlevel6task2.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.activityViewModels
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
import java.lang.Math.floor

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OverviewFragment : Fragment() {

    private val movies = arrayListOf<MovieItem>()
    private val viewModel: MovieViewModel by activityViewModels()

    private val movieAdapter = MovieAdapter(movies, ::onMovieClick)

    private var _binding: FragmentOverviewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRv()

        binding.btnSubmit.isEnabled = false

        binding.etYear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 4) {
                    binding.btnSubmit.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length == 4) {
                    binding.btnSubmit.isEnabled = true
                    binding.btnSubmit.setOnClickListener {
                        val year = binding.etYear.text.toString().toInt()
                        viewModel.getMoviesForYear(year)
                    }
                }
            }
        })

        observeMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRv() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 1, RecyclerView.VERTICAL, false)
        binding.rvMovies.layoutManager = gridLayoutManager
        // Add Global Layout Listener to calculate the span count.
        binding.rvMovies.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.rvMovies.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.spanCount = calculateSpanCount()
                gridLayoutManager.requestLayout()
            }
        })
        binding.rvMovies.adapter = movieAdapter
    }

    /**
     * Calculate the number of spans for the recycler view based on the recycler view width.
     * @return int number of spans.
     */
    private fun calculateSpanCount(): Int {
        val viewWidth = binding.rvMovies.measuredWidth
        val cardViewWidth = resources.getDimension(R.dimen.poster_width)
        val spanCount = kotlin.math.floor((viewWidth / (cardViewWidth)).toDouble()).toInt()
        return if (spanCount >= 1) spanCount else 1
    }

    private fun onMovieClick(movieItem: MovieItem) {
        viewModel.setMovie(movieItem)
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