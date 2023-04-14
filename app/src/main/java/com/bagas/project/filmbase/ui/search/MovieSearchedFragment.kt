package com.bagas.project.filmbase.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.responses.MovieSearchItem
import com.bagas.project.filmbase.databinding.FragmentMovieSearchedBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bagas.project.filmbase.ui.DetailActivity.Companion.EXTRA_MOVIE_DETAIL
import com.bagas.project.filmbase.ui.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchedFragment : Fragment() {

    private var _binding: FragmentMovieSearchedBinding? = null
    private val binding get() = _binding

    private val viewModel: SearchViewModel by viewModels()

    private val trendingMovieAdapter = ListTrendingMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieSearchedBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
//        val viewModel: SearchViewModel by viewModels {
//            factory
//        }

//        searchViewModel.listTrendingMovies.observe(viewLifecycleOwner) { listData ->
//            setTrendingMoviesData(listData)
//        }
//
//        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            showProgressBar(isLoading)
//        }

        showNotFound(false)

        viewModel.getTrendingMovies().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        binding?.trendingMovies?.visibility = View.VISIBLE
                        binding?.rvTrending?.visibility = View.VISIBLE
                        val trendingMoviesData = result.data
                        trendingMovieAdapter.submitList(trendingMoviesData)
                        showTrendingRv()
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

        binding?.searchview?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query!!.isNotEmpty() || query != "") {
                    binding?.trendingMovies?.visibility = View.GONE
                    binding?.rvTrending?.visibility = View.GONE
                    binding?.rvMovieSearched?.visibility = View.VISIBLE
                    viewModel.getMovieSearch(query)
                    viewModel.listMovieSearch.observe(viewLifecycleOwner) { result ->
                        if (result.size > 0) {
                            setMovieSearchData(result)
                            showMovieSearchedRv()
                            showNotFound(false)
                        } else {
                            binding?.rvMovieSearched?.visibility = View.GONE
                            showNotFound(true)
                        }
                    }
                    return true
                } else {
                    showNotFound(false)
                    binding?.trendingMovies?.visibility = View.VISIBLE
                    binding?.rvTrending?.visibility = View.VISIBLE
                    binding?.rvMovieSearched?.visibility = View.GONE
                    showTrendingRv()
                    return true
                }
            }

        })
    }

//    private fun setTrendingMoviesData(data: List<TrendingMoviesItem?>) {
//        val adapter = ListTrendingMovieAdapter(data, emptyList())
//        binding?.rvTrending?.adapter = adapter
//
//        adapter.setOnItemClickCallback(object : ListTrendingMovieAdapter.OnItemClickCallback{
//            override fun onItemClicked(
//                dataTrendingMovie: TrendingMoviesItem?,
//                dataTrendingTv: TrendingTvshowItem?
//            ) {
//                if (dataTrendingMovie != null) {
//                    val intent = Intent(requireActivity(), DetailActivity::class.java)
//                    intent.putExtra(EXTRA_MOVIE_DETAIL, dataTrendingMovie.id)
//                    startActivity(intent)
//                } else {
//                    val intent = Intent(requireActivity(), DetailActivity::class.java)
//                    intent.putExtra(EXTRA_TV_DETAIL, dataTrendingTv?.id)
//                    startActivity(intent)
//                }
//            }
//
//        })
//    }

    private fun showTrendingRv() {
        binding?.rvTrending?.adapter = trendingMovieAdapter
        binding?.rvTrending?.setHasFixedSize(true)
        binding?.rvTrending?.layoutManager = GridLayoutManager(requireActivity(), 3)
    }

    private fun setMovieSearchData(data: List<MovieSearchItem?>) {
        val adapter = ListSearchAdapter(data)
        binding?.rvMovieSearched?.adapter = adapter

        adapter.setOnItemClickCallback(object : ListSearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MovieSearchItem?) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(EXTRA_MOVIE_DETAIL, data?.id)
                startActivity(intent)
            }

        })
    }

    private fun showMovieSearchedRv() {
        binding?.rvMovieSearched?.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding?.rvMovieSearched?.setHasFixedSize(true)
    }

    private fun showNotFound(state: Boolean) {
        if (state) {
            binding?.lottieAnimation?.visibility = View.VISIBLE
        } else {
            binding?.lottieAnimation?.visibility = View.GONE
        }
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        binding?.searchview?.setQuery("", false)
    }
}