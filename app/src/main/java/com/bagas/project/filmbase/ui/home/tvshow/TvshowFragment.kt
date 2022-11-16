package com.bagas.project.filmbase.ui.home.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.data.Result
import com.bagas.project.filmbase.data.responses.AiringTodayTvshowItem
import com.bagas.project.filmbase.data.responses.TopRatedTvshowItem
import com.bagas.project.filmbase.databinding.FragmentTvshowBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bagas.project.filmbase.ui.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvshowFragment : Fragment() {

    private var _binding: FragmentTvshowBinding? = null
    private val binding get() = _binding

    private val viewModel: TvshowViewModel by viewModels()

    private val airingTodayTvshowAdapter = ListAiringTodayTvshowAdapter()
    private val topRatedTvshowAdapter = ListTopRatedTvshowAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvshowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
//        val viewModel: TvshowViewModel by viewModels {
//            factory
//        }

//        tvshowViewModel.listAiringTodayTvshow.observe(viewLifecycleOwner) { listData ->
//            setAiringTodayTvshowData(listData)
//        }
//
//        tvshowViewModel.listTopRatedTvshow.observe(viewLifecycleOwner) { listData ->
//            setTopRatedTvshowData(listData)
//        }
//
//        tvshowViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            showProgressbar(isLoading)
//        }

        viewModel.getAiringTodayTvshow().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val airingTodayTvData = result.data
                        airingTodayTvshowAdapter.submitList(airingTodayTvData)
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

        viewModel.getTopRatedTvshow().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val topRatedTvData = result.data
                        topRatedTvshowAdapter.submitList(topRatedTvData)
                    }
                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi Kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        showAiringTodayRv()
        showTopRatedTvshowRv()

    }

//    private fun setAiringTodayTvshowData(data: List<AiringTodayTvshowItem?>) {
//        val adapter = ListAiringTodayTvshowAdapter(data)
//        binding?.rvAiringTodayTvshow?.adapter = adapter
//
//        adapter.setOnItemClickCallback(object : ListAiringTodayTvshowAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: AiringTodayTvshowItem) {
//                val intent = Intent(requireActivity(), DetailActivity::class.java)
//                intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, data.id)
//                startActivity(intent)
//            }
//
//        })
//    }
//
//    private fun setTopRatedTvshowData(data: List<TopRatedTvshowItem?>) {
//        val adapter = ListTopRatedTvshowAdapter(data)
//        binding?.rvTopRatedTvshow?.adapter = adapter
//
//        adapter.setOnItemClickCallback(object : ListTopRatedTvshowAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: TopRatedTvshowItem?) {
//                val intent = Intent(requireActivity(), DetailActivity::class.java)
//                intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, data?.id)
//                startActivity(intent)
//            }
//
//        })
//    }

    private fun showAiringTodayRv() {
        binding?.rvAiringTodayTvshow?.adapter = airingTodayTvshowAdapter
        binding?.rvAiringTodayTvshow?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvAiringTodayTvshow?.setHasFixedSize(true)
    }

    private fun showTopRatedTvshowRv() {
        binding?.rvTopRatedTvshow?.adapter = topRatedTvshowAdapter
        binding?.rvTopRatedTvshow?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvTopRatedTvshow?.setHasFixedSize(true)
    }
}