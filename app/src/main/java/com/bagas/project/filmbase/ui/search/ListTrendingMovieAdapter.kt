package com.bagas.project.filmbase.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.local.TrendingMovieEntity
import com.bagas.project.filmbase.data.responses.TrendingMoviesItem
import com.bagas.project.filmbase.data.responses.TrendingTvshowItem
import com.bagas.project.filmbase.databinding.ItemRowTrendingBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bumptech.glide.Glide

class ListTrendingMovieAdapter: ListAdapter<TrendingMovieEntity, ListTrendingMovieAdapter.ListTrendingVH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTrendingMovieAdapter.ListTrendingVH {
        val binding = ItemRowTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTrendingVH(binding)
    }

    override fun onBindViewHolder(holder: ListTrendingMovieAdapter.ListTrendingVH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ListTrendingVH(private val binding: ItemRowTrendingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(trendingMovie: TrendingMovieEntity) {
            with(binding) {
                tvItemTitle.text = trendingMovie.title.toString().trim()
                tvItemRating.text = trendingMovie.voteAverage.toString().trim()
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + trendingMovie.posterPath)
                    .into(tvItemImg)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE_DETAIL, trendingMovie.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TrendingMovieEntity> =
            object : DiffUtil.ItemCallback<TrendingMovieEntity>() {
                override fun areItemsTheSame(
                    oldItem: TrendingMovieEntity,
                    newItem: TrendingMovieEntity
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: TrendingMovieEntity,
                    newItem: TrendingMovieEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}