package com.bagas.project.filmbase.ui.home.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.data.responses.AiringTodayTvshowItem
import com.bagas.project.filmbase.data.responses.TopRatedTvshowItem
import com.bagas.project.filmbase.databinding.FragmentTvshowBinding
import com.bagas.project.filmbase.ui.DetailActivity

class TvshowFragment : Fragment() {

    private var _binding: FragmentTvshowBinding? = null
    private val binding get() = _binding

    private val tvshowViewModel by viewModels<TvshowViewModel>()

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

        tvshowViewModel.listAiringTodayTvshow.observe(viewLifecycleOwner) { listData ->
            setAiringTodayTvshowData(listData)
        }

        tvshowViewModel.listTopRatedTvshow.observe(viewLifecycleOwner) { listData ->
            setTopRatedTvshowData(listData)
        }

        tvshowViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showProgressbar(isLoading)
        }

        showAiringTodayRv()
        showTopRatedTvshowRv()

    }

    private fun setAiringTodayTvshowData(data: List<AiringTodayTvshowItem?>) {
        val adapter = ListAiringTodayTvshowAdapter(data)
        binding?.rvAiringTodayTvshow?.adapter = adapter

        adapter.setOnItemClickCallback(object : ListAiringTodayTvshowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: AiringTodayTvshowItem) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, data.id)
                startActivity(intent)
            }

        })
    }

    private fun setTopRatedTvshowData(data: List<TopRatedTvshowItem?>) {
        val adapter = ListTopRatedTvshowAdapter(data)
        binding?.rvTopRatedTvshow?.adapter = adapter

        adapter.setOnItemClickCallback(object : ListTopRatedTvshowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TopRatedTvshowItem?) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, data?.id)
                startActivity(intent)
            }

        })
    }

    private fun showAiringTodayRv() {
        binding?.rvAiringTodayTvshow?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvAiringTodayTvshow?.setHasFixedSize(true)
    }

    private fun showTopRatedTvshowRv() {
        binding?.rvTopRatedTvshow?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvTopRatedTvshow?.setHasFixedSize(true)
    }

    private fun showProgressbar(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.INVISIBLE
        }
    }
}