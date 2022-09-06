package com.bagas.project.filmbase.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.local.FavoriteTvEntity
import com.bagas.project.filmbase.databinding.ItemRowTrendingBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bumptech.glide.Glide

class FavoritedTvAdapter: ListAdapter<FavoriteTvEntity, FavoritedTvAdapter.FavoritedTvVH>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritedTvAdapter.FavoritedTvVH {
        val binding = ItemRowTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritedTvVH(binding)
    }

    override fun onBindViewHolder(holder: FavoritedTvAdapter.FavoritedTvVH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class FavoritedTvVH(private val binding: ItemRowTrendingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteTv: FavoriteTvEntity) {
            with(binding) {
                tvItemTitle.text = favoriteTv.name
                tvItemRating.text = favoriteTv.voteAverage.toString().trim()

                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + favoriteTv.posterPath)
                    .into(tvItemImg)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, favoriteTv.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteTvEntity> =
            object : DiffUtil.ItemCallback<FavoriteTvEntity>() {
                override fun areItemsTheSame(
                    oldItem: FavoriteTvEntity,
                    newItem: FavoriteTvEntity
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: FavoriteTvEntity,
                    newItem: FavoriteTvEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }

    }
}