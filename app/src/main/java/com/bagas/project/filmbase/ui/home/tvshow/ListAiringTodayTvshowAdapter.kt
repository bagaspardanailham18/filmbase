package com.bagas.project.filmbase.ui.home.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.local.AiringTodayTvEntity
import com.bagas.project.filmbase.data.responses.AiringTodayTvshowItem
import com.bagas.project.filmbase.databinding.ItemRowUpcomingBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bumptech.glide.Glide

class ListAiringTodayTvshowAdapter: ListAdapter<AiringTodayTvEntity, ListAiringTodayTvshowAdapter.ListAiringTodayTvshowVH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAiringTodayTvshowVH {
        val binding = ItemRowUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListAiringTodayTvshowVH(binding)
    }

    override fun onBindViewHolder(holder: ListAiringTodayTvshowVH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ListAiringTodayTvshowVH(private val binding: ItemRowUpcomingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(airingTodayTvEntity: AiringTodayTvEntity) {
            with(binding) {
                tvItemTitle.text = airingTodayTvEntity.name.toString().trim()
                tvItemRating.text = airingTodayTvEntity.voteAverage.toString().trim()

                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + airingTodayTvEntity.posterPath)
                    .into(tvItemImg)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, airingTodayTvEntity.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<AiringTodayTvEntity> =
            object : DiffUtil.ItemCallback<AiringTodayTvEntity>() {
                override fun areItemsTheSame(
                    oldItem: AiringTodayTvEntity,
                    newItem: AiringTodayTvEntity
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: AiringTodayTvEntity,
                    newItem: AiringTodayTvEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: AiringTodayTvshowItem)
    }

}