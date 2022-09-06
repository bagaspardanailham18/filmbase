package com.bagas.project.filmbase.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.local.TrendingTvshowEntity
import com.bagas.project.filmbase.databinding.ItemRowTrendingBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bumptech.glide.Glide

class ListTrendingTvAdapter: ListAdapter<TrendingTvshowEntity, ListTrendingTvAdapter.ListTrendingTvVH>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTrendingTvAdapter.ListTrendingTvVH {
        val binding = ItemRowTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTrendingTvVH(binding)
    }

    override fun onBindViewHolder(holder: ListTrendingTvAdapter.ListTrendingTvVH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ListTrendingTvVH(private val binding: ItemRowTrendingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(trendingTv: TrendingTvshowEntity) {
            with(binding) {
                tvItemTitle.text = trendingTv.name.toString().trim()
                tvItemRating.text = trendingTv.voteAverage.toString().trim()
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + trendingTv.posterPath)
                    .into(tvItemImg)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, trendingTv.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TrendingTvshowEntity> =
            object : DiffUtil.ItemCallback<TrendingTvshowEntity>() {
                override fun areItemsTheSame(
                    oldItem: TrendingTvshowEntity,
                    newItem: TrendingTvshowEntity
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: TrendingTvshowEntity,
                    newItem: TrendingTvshowEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}