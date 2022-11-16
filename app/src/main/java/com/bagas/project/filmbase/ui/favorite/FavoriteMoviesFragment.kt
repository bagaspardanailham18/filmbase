package com.bagas.project.filmbase.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.local.FavoriteMovieEntity
import com.bagas.project.filmbase.databinding.FragmentFavoriteMoviesBinding
import com.bagas.project.filmbase.ui.ViewModelFactory
import com.bagas.project.filmbase.ui.home.movie.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding

    private val viewModel: FavoriteViewModel by viewModels()

    private val adapter = FavoritedMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
//        val viewModel: FavoriteViewModel by viewModels {
//            factory
//        }

        viewModel.getFavoriteMovies().observe(viewLifecycleOwner) { favoritedMovies ->
            if (favoritedMovies.isNotEmpty()) {
                binding?.progressBar?.visibility = View.GONE
                adapter.submitList(favoritedMovies)
            } else {
                binding?.lottieAnimation?.visibility = View.VISIBLE
                adapter.submitList(favoritedMovies)
            }
        }

        binding?.searchview?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(q: String?): Boolean {
                if (q!!.isNotEmpty() || q != "") {
                    viewModel.getFavoriteMoviesByQuery(q.trim()).observe(requireActivity()) { result ->
                        if (result.size > 0) {
                            binding?.rvMovieSearched?.visibility = View.VISIBLE
                            binding?.rvFavoriteMovie?.visibility = View.GONE
                            showMovieSearchData(result)
                            showNotFound(false)
                        } else {
                            showNotFound(true)
                            binding?.rvMovieSearched?.visibility = View.GONE
                            binding?.rvFavoriteMovie?.visibility = View.GONE
                            binding?.lottieAnimation?.visibility = View.GONE
                        }
                    }
                    return true
                } else {

                    viewModel.getFavoriteMovies().observe(viewLifecycleOwner) { favoritedMovies ->
                        if (favoritedMovies.isNotEmpty()) {
                            binding?.progressBar?.visibility = View.GONE
                            adapter.submitList(favoritedMovies)
                        } else {
                            binding?.lottieAnimation?.visibility = View.VISIBLE
                            adapter.submitList(favoritedMovies)
                        }
                    }

                    showNotFound(false)
                    binding?.rvFavoriteMovie?.visibility = View.VISIBLE
                    binding?.rvMovieSearched?.visibility = View.GONE
                    showFavoritedMoviesRv()
                    return true
                }
            }
        })

        showFavoritedMoviesRv()
    }

    private fun showFavoritedMoviesRv() {
        binding?.rvFavoriteMovie?.visibility = View.VISIBLE
        binding?.rvFavoriteMovie?.adapter = adapter
        binding?.rvFavoriteMovie?.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding?.rvFavoriteMovie?.setHasFixedSize(true)
    }

    private fun showMovieSearchData(result: List<FavoriteMovieEntity>) {
        adapter.submitList(result)
        binding?.rvMovieSearched?.adapter = adapter
        binding?.rvMovieSearched?.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding?.rvMovieSearched?.setHasFixedSize(true)
    }

    private fun showNotFound(state: Boolean) {
        if (state) {
            binding?.lottieNotfoundAnimation?.visibility = View.VISIBLE
        } else {
            binding?.lottieNotfoundAnimation?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}