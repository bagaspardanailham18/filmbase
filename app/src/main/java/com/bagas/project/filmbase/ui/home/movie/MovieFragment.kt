package com.bagas.project.filmbase.ui.home.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.responses.TopRatedMoviesItem
import com.bagas.project.filmbase.data.responses.UpcomingMoviesItem
import com.bagas.project.filmbase.databinding.FragmentMovieBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bagas.project.filmbase.ui.DetailActivity.Companion.EXTRA_MOVIE_DETAIL
import com.bagas.project.filmbase.ui.SeeAllActivity
import com.bagas.project.filmbase.ui.SeeAllActivity.Companion.SEE_ALL_TYPE
import com.bagas.project.filmbase.ui.SeeAllActivity.Companion.TOP_RATED_MOVIES
import com.bagas.project.filmbase.ui.SeeAllActivity.Companion.UPCOMING_MOVIES
import com.bagas.project.filmbase.ui.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding

    private val viewModel: MovieViewModel by viewModels()

    private val upcomingMovieAdapter = ListUpcomingMovieAdapter()
    private val topRatedMovieAdapter = ListTopRatedMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
//        val viewModel: MovieViewModel by viewModels {
//            factory
//        }

        getUpcomingMoviesData()
        getTopRatedMoviesData()

        showUpcomingMoviesRv()
        showTopRatedMoviesRv()

        binding?.seeAllUpcoming?.setOnClickListener {
            val toSeeAllUpcoming = Intent(requireActivity(), SeeAllActivity::class.java)
            toSeeAllUpcoming.putExtra(SEE_ALL_TYPE, UPCOMING_MOVIES)
            startActivity(toSeeAllUpcoming)
        }

        binding?.seeAllTop?.setOnClickListener {
            val toSeeAllTop = Intent(requireActivity(), SeeAllActivity::class.java)
            toSeeAllTop.putExtra(SEE_ALL_TYPE, TOP_RATED_MOVIES)
            startActivity(toSeeAllTop)
        }
    }

    private fun getUpcomingMoviesData() {
        viewModel.getUpcomingMovies().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val upcomingMoviesData = result.data
                        upcomingMovieAdapter.submitList(upcomingMoviesData)
                    }
                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun getTopRatedMoviesData() {
        viewModel.getTopRatedMovies().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val topRatedMoviesData = result.data
                        topRatedMovieAdapter.submitList(topRatedMoviesData)
                    }
                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showUpcomingMoviesRv() {
        binding?.rvUpcomingMovie?.adapter = upcomingMovieAdapter
        binding?.rvUpcomingMovie?.setHasFixedSize(true)
        binding?.rvUpcomingMovie?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showTopRatedMoviesRv() {
        binding?.rvTopRatedMovie?.adapter = topRatedMovieAdapter
        binding?.rvTopRatedMovie?.setHasFixedSize(true)
        binding?.rvTopRatedMovie?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}