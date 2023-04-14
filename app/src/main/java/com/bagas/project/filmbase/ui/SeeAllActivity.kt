package com.bagas.project.filmbase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.data.responses.UpcomingMoviesItem
import com.bagas.project.filmbase.databinding.ActivitySeeAllBinding
import com.bagas.project.filmbase.ui.home.movie.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeeAllBinding

    private val movieViewModel by viewModels<MovieViewModel>()

    companion object {
        const val UPCOMING_MOVIES = "Upcoming Movies"
        const val TOP_RATED_MOVIES = "Top Rated Movies"
        const val SEE_ALL_TYPE = "see_all_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.extras?.get(SEE_ALL_TYPE).toString().trim()

        binding.customToolbar.title = type
        binding.customToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.customToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

//        if (type == UPCOMING_MOVIES) {
//            movieViewModel.totapPages.observe(this) { totalPages ->
//                movieViewModel.getAllUpcomingMovies(totalPages)
//                movieViewModel.listAllUpcomingMovies.observe(this) { listAllUpcomingMovies ->
//                    setAllUpcomingMoviesData(listAllUpcomingMovies)
//                }
//            }
//
//            movieViewModel.isLoading.observe(this) { isLoading ->
//                showProgressBar(isLoading)
//            }
//        } else {
//            return
//        }

        showAllUpcomingMoviesRv()

    }

    private fun setAllUpcomingMoviesData(data: List<UpcomingMoviesItem?>) {
        val adapter = SeeAllAdapter(data)
        binding.rvSeeAll.adapter = adapter
    }

    private fun showAllUpcomingMoviesRv() {
        binding.rvSeeAll.layoutManager = LinearLayoutManager(this)
        binding.rvSeeAll.setHasFixedSize(true)
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}