package com.bagas.project.filmbase.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.data.local.entities.FavoriteMovieEntity
import com.bagas.project.filmbase.data.local.entities.FavoriteTvEntity
import com.bagas.project.filmbase.data.responses.*
import com.bagas.project.filmbase.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>()

//    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
//    private val detailViewModel: DetailViewModel by viewModels {
//        factory
//    }

    private var movieId: Int? = null
    private var tvshowId: Int? = null

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

        movieId = intent.getIntExtra(EXTRA_MOVIE_DETAIL, 0)
        tvshowId = intent.getIntExtra(EXTRA_TV_DETAIL, 0)

        if (movieId != 0) {
            populateMovieDetail(movieId)
        } else {
            populateTvDetail(tvshowId)
        }

    }

    private fun populateMovieDetail(data: Int?) {

        lifecycleScope.launch {
            detailViewModel.getMovieDetail(data)
            detailViewModel.movieDetail.observe(this@DetailActivity) { data ->
                when (data) {
                    is com.bagas.project.filmbase.data.Result.Loading -> {
                        Log.d("detail", "loading")
                    }
                    is com.bagas.project.filmbase.data.Result.Success -> {
                        Log.d("detail", data.data.toString())
                        binding.apply {
                            tvDetailTitle.text = data.data?.title
                            tvDetailDate.text = data.data?.releaseDate

                            if (data.data?.genres!!.isNotEmpty()) {
                                tvDetailGenre.text = data.data?.genres.get(0)?.name.toString()
                            } else {
                                tvDetailGenre.text = "-"
                            }

                            tvDetailFavorites.text = data.data.voteAverage?.toString()?.trim()
                            if (data.data.overview.equals("")) {
                                binding.tvDetailOverview.text = "no overview"
                            } else {
                                binding.tvDetailOverview.text = data.data?.overview
                            }

                            Glide.with(this@DetailActivity)
                                .load(BuildConfig.IMAGE_URL + data.data.backdropPath)
                                .into(binding.backdrop)

                            Glide.with(this@DetailActivity)
                                .load(BuildConfig.IMAGE_URL + data.data.posterPath)
                                .into(binding.tvDetailPoster)

                            detailViewModel.getFavoritedMovieById(data.data.id).observe(this@DetailActivity) { favoritedData ->
                                if (favoritedData != null) {
                                    setFavoriteState(true)
                                    binding.btnToggleFavorite.setOnClickListener { detailViewModel.deleteFavoritedMovie(favoritedData) }
                                } else {
                                    setFavoriteState(false)
                                    val movie = FavoriteMovieEntity(
                                        data.data.id,
                                        data.data.title,
                                        data.data.overview,
                                        data.data.releaseDate,
                                        data.data.voteAverage,
                                        data.data.posterPath.toString(),
                                        data.data.backdropPath
                                    )
                                    binding.btnToggleFavorite.setOnClickListener { detailViewModel.insertFavoritedMovie(movie) }
                                }
                            }

                            populateMovieProduction(data.data.productionCompanies)
                        }
                    }
                    is com.bagas.project.filmbase.data.Result.Error -> {
                        Log.d("detail", "error")
                    }
                }
            }

            detailViewModel.getMovieVideos(data)
            Log.d("movieId", data.toString())
            detailViewModel.movieVideos.observe(this@DetailActivity) { video ->
                when (video) {
                    is com.bagas.project.filmbase.data.Result.Loading -> {
                        Log.d("detail", "Videos : Loading")
                    }
                    is com.bagas.project.filmbase.data.Result.Success -> {
                        Log.d("detail", "Videos : Success")
                        binding.btnTrailer.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.WATCH_YOUTUBE_URL + video.data?.results?.get(0)?.key))
                            startActivity(intent)
                        }

                        // Populate RV Videos
                        populateMovieVideos(video.data?.results)
                    }
                    is com.bagas.project.filmbase.data.Result.Error -> {
                        Log.d("detail", "Videos : Error")
                    }
                }
            }
        }

    }

    private fun populateMovieVideos(data: List<MovieVideoItem?>?) {
        binding.rvVideos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = data?.let { VideosAdapter(it, emptyList()) }
        binding.rvVideos.adapter = adapter
        binding.rvVideos.setHasFixedSize(true)
    }

    private fun populateMovieProduction(data: List<MovieProductionCompaniesItem?>?) {
        binding.rvProductions.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ProductionsAdapter(data!!, emptyList())
        binding.rvProductions.adapter = adapter
        binding.rvProductions.setHasFixedSize(true)
    }

    private fun populateTvDetail(data: Int?) {
        lifecycleScope.launch {
            detailViewModel.getTvshowDetail(data)
            detailViewModel.tvDetail.observe(this@DetailActivity) { data ->
                when (data) {
                    is com.bagas.project.filmbase.data.Result.Loading -> {

                    }
                    is com.bagas.project.filmbase.data.Result.Success -> {
                        binding.apply {
                            tvDetailTitle.text = data.data?.name
                            tvDetailDate.text = data.data?.firstAirDate

                            if (data.data?.genres!!.isNotEmpty()) {
                                tvDetailGenre.text = data.data.genres[0]?.name.toString()
                            } else {
                                tvDetailGenre.text = "-"
                            }

                            tvDetailFavorites.text = data.data.voteAverage?.toString()?.trim()

                            if (!data.data.overview.equals("")) {
                                tvDetailOverview.text = data.data.overview
                            } else {
                                tvDetailOverview.text = "No Overview"
                            }

                            Glide.with(this@DetailActivity)
                                .load(BuildConfig.IMAGE_URL + data.data.backdropPath)
                                .error(R.drawable.image_load_error)
                                .placeholder(R.drawable.image_loading_placeholder)
                                .into(backdrop)

                            Glide.with(this@DetailActivity)
                                .load(BuildConfig.IMAGE_URL + data.data.posterPath)
                                .error(R.drawable.image_load_error)
                                .placeholder(R.drawable.image_loading_placeholder)
                                .into(tvDetailPoster)

                            detailViewModel.getFavoritedTvById(data.data.id).observe(this@DetailActivity) { favoritedData ->
                                if (favoritedData != null) {
                                    setFavoriteState(true)
                                    btnToggleFavorite.setOnClickListener { detailViewModel.deleteFavoritedTv(favoritedData) }
                                } else {
                                    setFavoriteState(false)
                                    val tv = FavoriteTvEntity(
                                        data.data.id!!,
                                        data.data.name,
                                        data.data.overview,
                                        data.data.firstAirDate,
                                        data.data.voteAverage,
                                        data.data.posterPath,
                                        data.data.backdropPath
                                    )
                                    btnToggleFavorite.setOnClickListener { detailViewModel.insertFavoritedTv(tv) }
                                }
                            }
                            populateTvProduction(data.data.productionCompanies)
                        }
                    }
                    is com.bagas.project.filmbase.data.Result.Error -> {

                    }
                }
            }

            detailViewModel.getTvVideos(data)
            detailViewModel.tvVideos.observe(this@DetailActivity) { video ->
                when (video) {
                    is com.bagas.project.filmbase.data.Result.Loading -> {

                    }
                    is com.bagas.project.filmbase.data.Result.Success -> {
                        binding.btnTrailer.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.WATCH_YOUTUBE_URL + video.data?.results?.get(0)?.key))
                            startActivity(intent)
                        }

                        populateTvVideos(video.data?.results!!)
                    }
                    is com.bagas.project.filmbase.data.Result.Error -> {

                    }
                }
            }
        }
    }

    private fun populateTvVideos(data: List<TvVideoItem?>) {
        detailViewModel.tvVideos.observe(this) { videos ->
            binding.rvVideos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val adapter = VideosAdapter(emptyList(), data)
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

    private fun setFavoriteState(state: Boolean) {
        val fab = binding.btnToggleFavorite
        if (state) {
            fab.isChecked = true
        } else {
            fab.isChecked = false
        }
    }
}