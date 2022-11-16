package com.bagas.project.filmbase.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.data.local.FavoriteMovieEntity
import com.bagas.project.filmbase.data.local.FavoriteTvEntity
import com.bagas.project.filmbase.databinding.FragmentFavoriteTvshowsBinding
import com.bagas.project.filmbase.ui.ViewModelFactory
import com.bagas.project.filmbase.ui.home.tvshow.TvshowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTvshowsFragment : Fragment() {

    private var _binding: FragmentFavoriteTvshowsBinding? = null
    private val binding get() = _binding

    private val viewModel: FavoriteViewModel by viewModels()

    private val adapter = FavoritedTvAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteTvshowsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
//        val viewModel: FavoriteViewModel by viewModels {
//            factory
//        }

        viewModel.getFavoriteTvshows().observe(viewLifecycleOwner) { favoritedTv ->
            if (favoritedTv.isNotEmpty()) {
                binding?.progressBar?.visibility = View.GONE
                adapter.submitList(favoritedTv)
            } else {
                binding?.lottieAnimation?.visibility = View.VISIBLE
                adapter.submitList(favoritedTv)
            }
        }

        binding?.searchview?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(q: String?): Boolean {
                if (q!!.isNotEmpty() || q != "") {
                    viewModel.getFavoriteTvByQuery(q.trim()).observe(requireActivity()) { result ->
                        if (result.size > 0) {
                            binding?.rvTvshowSearched?.visibility = View.VISIBLE
                            binding?.rvFavoriteTvshow?.visibility = View.GONE
                            showTvSearchData(result)
                            showNotFound(false)
                        } else {
                            showNotFound(true)
                            binding?.rvTvshowSearched?.visibility = View.GONE
                            binding?.rvFavoriteTvshow?.visibility = View.GONE
                            binding?.lottieAnimation?.visibility = View.GONE
                        }
                    }
                    return true
                } else {

                    viewModel.getFavoriteTvshows().observe(viewLifecycleOwner) { favoritedTv ->
                        if (favoritedTv.isNotEmpty()) {
                            binding?.progressBar?.visibility = View.GONE
                            adapter.submitList(favoritedTv)
                        } else {
                            binding?.lottieAnimation?.visibility = View.VISIBLE
                            adapter.submitList(favoritedTv)
                        }
                    }

                    showNotFound(false)
                    binding?.rvFavoriteTvshow?.visibility = View.VISIBLE
                    binding?.rvTvshowSearched?.visibility = View.GONE
                    showFavoritedTvRv()
                    return true
                }
            }
        })

        showFavoritedTvRv()
    }

    private fun showFavoritedTvRv() {
        binding?.rvFavoriteTvshow?.visibility = View.VISIBLE
        binding?.rvFavoriteTvshow?.adapter = adapter
        binding?.rvFavoriteTvshow?.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding?.rvFavoriteTvshow?.setHasFixedSize(true)
    }

    private fun showTvSearchData(result: List<FavoriteTvEntity>) {
        adapter.submitList(result)
        binding?.rvTvshowSearched?.adapter = adapter
        binding?.rvTvshowSearched?.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding?.rvTvshowSearched?.setHasFixedSize(true)
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