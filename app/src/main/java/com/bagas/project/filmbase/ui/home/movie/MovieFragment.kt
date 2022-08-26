package com.bagas.project.filmbase.ui.home.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.project.filmbase.data.responses.TopRatedMoviesItem
import com.bagas.project.filmbase.data.responses.UpcomingMoviesItem
import com.bagas.project.filmbase.databinding.FragmentMovieBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bagas.project.filmbase.ui.DetailActivity.Companion.EXTRA_MOVIE_DETAIL
import com.bagas.project.filmbase.ui.SeeAllActivity
import com.bagas.project.filmbase.ui.SeeAllActivity.Companion.SEE_ALL_TYPE
import com.bagas.project.filmbase.ui.SeeAllActivity.Companion.TOP_RATED_MOVIES
import com.bagas.project.filmbase.ui.SeeAllActivity.Companion.UPCOMING_MOVIES

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding

    private val movieViewModel by viewModels<MovieViewModel>()

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

        movieViewModel.listUpcomingMovies.observe(viewLifecycleOwner) { listUpcomingMovie ->
            setUpcomingMoviesData(listUpcomingMovie)
            Log.d("MovieFragment", listUpcomingMovie.toString())
        }

        movieViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showProgressBar(isLoading)
        }

        movieViewModel.listTopRatedMovies.observe(viewLifecycleOwner) { listTopRatedMovies ->
            setTopRatedMoviesData(listTopRatedMovies)
        }

        showUpcomingMoviesRv()
        showTopRatedMoviesRv()

        binding?.seeAllUpcoming?.setOnClickListener {
//            val toSeeAllActivity = MovieFragmentDirections.actionMovieFragmentToSeeAllActivity()
//            toSeeAllActivity.seeAllType = UPCOMING_MOVIES
//            Navigation.findNavController(view).navigate(toSeeAllActivity)
            val toSeeAllUpcoming = Intent(requireActivity(), SeeAllActivity::class.java)
            toSeeAllUpcoming.putExtra(SEE_ALL_TYPE, UPCOMING_MOVIES)
            startActivity(toSeeAllUpcoming)
        }

        binding?.seeAllTop?.setOnClickListener {
//            val toSeeAllActivity = MovieFragmentDirections.actionMovieFragmentToSeeAllActivity()
//            toSeeAllActivity.seeAllType = TOP_RATED_MOVIES
//            Navigation.findNavController(view).navigate(toSeeAllActivity)
            val toSeeAllTop = Intent(requireActivity(), SeeAllActivity::class.java)
            toSeeAllTop.putExtra(SEE_ALL_TYPE, TOP_RATED_MOVIES)
            startActivity(toSeeAllTop)
        }
    }


    private fun setUpcomingMoviesData(data: List<UpcomingMoviesItem?>) {
        val adapter = ListUpcomingMovieAdapter(data)
        binding?.rvUpcomingMovie?.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUpcomingMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UpcomingMoviesItem) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(EXTRA_MOVIE_DETAIL, data.id)
                startActivity(intent)
            }

        })
    }

    private fun setTopRatedMoviesData(data: List<TopRatedMoviesItem>) {
        val adapter = ListTopRatedMovieAdapter(data)
        binding?.rvTopRatedMovie?.adapter = adapter

        adapter.setOnItemClickCallback(object : ListTopRatedMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TopRatedMoviesItem) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(EXTRA_MOVIE_DETAIL, data.id)
                startActivity(intent)
            }

        })
    }

    private fun showUpcomingMoviesRv() {
        binding?.rvUpcomingMovie?.setHasFixedSize(true)
        binding?.rvUpcomingMovie?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showTopRatedMoviesRv() {
        binding?.rvTopRatedMovie?.setHasFixedSize(true)
        binding?.rvTopRatedMovie?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}