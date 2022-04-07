package com.example.madlevel6task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.madlevel6task2.databinding.FragmentMovieBinding
import com.example.madlevel6task2.model.MovieItem
import com.example.madlevel6task2.model.MovieViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null

//    private lateinit var movie: MovieItem
    private val viewModel: MovieViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMovie()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeMovie() {

        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            Glide.with(requireContext()).load(it.getPosterImageUrl()).into(binding.ivPoster)
            Glide.with(requireContext()).load(it.getBackdropImageUrl()).into(binding.ivBackdrop)
            binding.tvReleaseDate.text = it.releaseDate
            binding.tvTitle.text = it.title
            binding.tvRating.text = it.rating.toString()
            binding.tvOverviewText.text = it.overview
        }
    }
}