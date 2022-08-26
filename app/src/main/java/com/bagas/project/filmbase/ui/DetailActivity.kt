package com.bagas.project.filmbase.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.MovieProductionCompaniesItem
import com.bagas.project.filmbase.data.responses.TvProductionCompaniesItem
import com.bagas.project.filmbase.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import retrofit2.Callback

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_MOVIE_DETAIL = "extra_movie_detail"
        const val EXTRA_TV_DETAIL = "extra_tv_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupStatusBar()

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        val movieData = intent.getIntExtra(EXTRA_MOVIE_DETAIL, 0)
        val tvData = intent.getIntExtra(EXTRA_TV_DETAIL, 0)

        if (movieData != 0) {
            populateMovieDetail(movieData)
        } else {
            populateTvDetail(tvData)
        }

    }

    private fun populateMovieDetail(data: Int) {
        detailViewModel.getMovieDetail(data)
        detailViewModel.movieDetail.observe(this) { data ->
            detailViewModel.getMovieVideos(data?.id)

            binding.tvDetailTitle.text = data?.title
            binding.tvDetailDate.text = data?.releaseDate
            binding.tvDetailGenre.text = data?.genres?.get(0)?.name.toString()
            binding.tvDetailFavorites.text = data?.voteAverage?.toString()?.trim()
            binding.tvDetailOverview.text = data?.overview

            Glide.with(this)
                .load(BuildConfig.IMAGE_URL + data?.backdropPath)
                .into(binding.backdrop)

            Glide.with(this)
                .load(BuildConfig.IMAGE_URL + data?.posterPath)
                .into(binding.tvDetailPoster)

            detailViewModel.movieVideos.observe(this) { video ->
                binding.btnTrailer.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.WATCH_YOUTUBE_URL + video[0]?.key))
                    startActivity(intent)
                }
            }

            populateMovieVideos()
            populateMovieProduction(data?.productionCompanies)
        }
    }

    private fun populateMovieVideos() {
        detailViewModel.movieVideos.observe(this) { videos ->
            binding.rvVideos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val adapter = VideosAdapter(videos, emptyList())
            binding.rvVideos.adapter = adapter
            binding.rvVideos.setHasFixedSize(true)
        }
    }

    private fun populateMovieProduction(data: List<MovieProductionCompaniesItem?>?) {
        binding.rvProductions.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ProductionsAdapter(data!!, emptyList())
        binding.rvProductions.adapter = adapter
        binding.rvProductions.setHasFixedSize(true)
    }

    private fun populateTvDetail(data: Int) {
        detailViewModel.getTvshowDetail(data)
        detailViewModel.tvDetail.observe(this) { data ->
            detailViewModel.getTvVideos(data?.id)

            binding.tvDetailTitle.text = data?.name
            binding.tvDetailDate.text = data?.firstAirDate

            if (data?.genres!!.isNotEmpty()) {
                binding.tvDetailGenre.text = data.genres.get(0)?.name.toString()
            } else {
                binding.tvDetailGenre.text = "-"
            }

            binding.tvDetailFavorites.text = data.voteAverage?.toString()?.trim()

            if (!data.overview.equals("")) {
                binding.tvDetailOverview.text = data.overview
            } else {
                binding.tvDetailOverview.text = "No Overview"
            }

            Glide.with(this)
                .load(BuildConfig.IMAGE_URL + data.backdropPath)
                .into(binding.backdrop)

            Glide.with(this)
                .load(BuildConfig.IMAGE_URL + data.posterPath)
                .into(binding.tvDetailPoster)

            detailViewModel.tvVideos.observe(this) { video ->
                binding.btnTrailer.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.WATCH_YOUTUBE_URL + video[0]?.key))
                    startActivity(intent)
                }
            }

            populateTvVideos()
            populateTvProduction(data.productionCompanies)
        }
    }

    private fun populateTvVideos() {
        detailViewModel.tvVideos.observe(this) { videos ->
            binding.rvVideos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val adapter = VideosAdapter(emptyList(), videos)
            binding.rvVideos.adapter = adapter
            binding.rvVideos.setHasFixedSize(true)
        }
    }

    private fun populateTvProduction(data: List<TvProductionCompaniesItem?>?) {
        binding.rvProductions.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ProductionsAdapter(emptyList(), data!!)
        binding.rvProductions.adapter = adapter
        binding.rvProductions.setHasFixedSize(true)
    }

    private fun setupStatusBar() {
        with(window) {
            setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }
}