package com.bagas.project.filmbase.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.data.responses.TrendingMoviesItem
import com.bagas.project.filmbase.data.responses.TrendingTvshowItem
import com.bagas.project.filmbase.data.responses.TvshowSearchItem
import com.bagas.project.filmbase.databinding.FragmentTvshowSearchedBinding
import com.bagas.project.filmbase.ui.DetailActivity

class TvshowSearchedFragment : Fragment() {

    private var _binding: FragmentTvshowSearchedBinding? = null
    private val binding get() = _binding

    private val searchViewModel by viewModels<SearchViewModel>()

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

        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showProgressBar(isLoading)
        }

        searchViewModel.listTrendingTvshow.observe(viewLifecycleOwner) { listData ->
            setTrendingTvshowData(listData)
        }

        showTrendingRv()
        showNotFound(false)

        binding?.searchview?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query!!.isNotEmpty() || query != "") {
                    binding?.trendingTvshow?.visibility = View.INVISIBLE
                    binding?.rvTvshowSearched?.visibility = View.VISIBLE
                    searchViewModel.getTvshowSearch(query)
                    searchViewModel.listTvshowSearch.observe(viewLifecycleOwner) { result ->
                        if (result.size > 0) {
                            setTvshowSearchData(result)
                            showTvshowSearchedRv()
                            showNotFound(false)
                        } else {
                            binding?.rvTvshowSearched?.visibility = View.INVISIBLE
                            showNotFound(true)
                        }
                    }
                    return true
                } else {
                    showNotFound(false)
                    binding?.trendingTvshow?.visibility = View.VISIBLE
                    binding?.rvTvshowSearched?.visibility = View.INVISIBLE
                    showTrendingRv()
                    return true
                }
            }

        })
    }

    private fun setTrendingTvshowData(data: List<TrendingTvshowItem?>) {
        val adapter = ListTrendingAdapter(emptyList(), data)
        binding?.rvTrending?.adapter = adapter

        adapter.setOnItemClickCallback(object : ListTrendingAdapter.OnItemClickCallback{
            override fun onItemClicked(
                dataTrendingMovie: TrendingMoviesItem?,
                dataTrendingTv: TrendingTvshowItem?
            ) {
                if (dataTrendingMovie != null) {
                    val intent = Intent(requireActivity(), DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE_DETAIL, dataTrendingMovie.id)
                    startActivity(intent)
                } else {
                    val intent = Intent(requireActivity(), DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, dataTrendingTv?.id)
                    startActivity(intent)
                }
            }

        })
    }

    private fun showTrendingRv() {
        binding?.rvTrending?.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding?.rvTrending?.setHasFixedSize(true)
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
            binding?.lottieAnimation?.visibility = View.INVISIBLE
        }
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }
}