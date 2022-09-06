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
import com.bagas.project.filmbase.data.responses.TrendingMoviesItem
import com.bagas.project.filmbase.data.responses.TrendingTvshowItem
import com.bagas.project.filmbase.data.responses.TvshowSearchItem
import com.bagas.project.filmbase.databinding.FragmentTvshowSearchedBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bagas.project.filmbase.ui.ViewModelFactory

class TvshowSearchedFragment : Fragment() {

    private var _binding: FragmentTvshowSearchedBinding? = null
    private val binding get() = _binding

//    private val searchViewModel by viewModels<SearchViewModel>()

    private val trendingTvAdapter = ListTrendingTvAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvshowSearchedBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: SearchViewModel by viewModels {
            factory
        }

//        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            showProgressBar(isLoading)
//        }
//
//        searchViewModel.listTrendingTvshow.observe(viewLifecycleOwner) { listData ->
//            setTrendingTvshowData(listData)
//        }


        showNotFound(false)

        viewModel.getTrendingTvshow().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        binding?.trendingTvshow?.visibility = View.VISIBLE
                        binding?.rvTrending?.visibility = View.VISIBLE
                        val trendingTvData = result.data
                        trendingTvAdapter.submitList(trendingTvData)
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
                    binding?.trendingTvshow?.visibility = View.GONE
                    binding?.rvTrending?.visibility = View.GONE
                    binding?.rvTvshowSearched?.visibility = View.VISIBLE
                    viewModel.getTvshowSearch(query)
                    viewModel.listTvshowSearch.observe(viewLifecycleOwner) { result ->
                        if (result.size > 0) {
                            setTvshowSearchData(result)
                            showTvshowSearchedRv()
                            showNotFound(false)
                        } else {
                            binding?.rvTvshowSearched?.visibility = View.GONE
                            showNotFound(true)
                        }
                    }
                    return true
                } else {
                    showNotFound(false)
                    binding?.trendingTvshow?.visibility = View.VISIBLE
                    binding?.rvTrending?.visibility = View.VISIBLE
                    binding?.rvTvshowSearched?.visibility = View.GONE
                    showTrendingRv()
                    return true
                }
            }

        })
    }

//    private fun setTrendingTvshowData(data: List<TrendingTvshowItem?>) {
//        val adapter = ListTrendingMovieAdapter(emptyList(), data)
//        binding?.rvTrending?.adapter = adapter
//
//        adapter.setOnItemClickCallback(object : ListTrendingMovieAdapter.OnItemClickCallback{
//            override fun onItemClicked(
//                dataTrendingMovie: TrendingMoviesItem?,
//                dataTrendingTv: TrendingTvshowItem?
//            ) {
//                if (dataTrendingMovie != null) {
//                    val intent = Intent(requireActivity(), DetailActivity::class.java)
//                    intent.putExtra(DetailActivity.EXTRA_MOVIE_DETAIL, dataTrendingMovie.id)
//                    startActivity(intent)
//                } else {
//                    val intent = Intent(requireActivity(), DetailActivity::class.java)
//                    intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, dataTrendingTv?.id)
//                    startActivity(intent)
//                }
//            }
//
//        })
//    }

    private fun showTrendingRv() {
        binding?.rvTrending?.adapter = trendingTvAdapter
        binding?.rvTrending?.setHasFixedSize(true)
        binding?.rvTrending?.layoutManager = GridLayoutManager(requireActivity(), 3)
    }

    private fun setTvshowSearchData(data: List<TvshowSearchItem?>) {
        val adapter = ListSearchTvAdapter(data)
        binding?.rvTvshowSearched?.adapter = adapter

        adapter.setOnItemClickCallback(object : ListSearchTvAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvshowSearchItem?) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, data?.id)
                startActivity(intent)
            }

        })
    }

    private fun showTvshowSearchedRv() {
        binding?.rvTvshowSearched?.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding?.rvTvshowSearched?.setHasFixedSize(true)
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