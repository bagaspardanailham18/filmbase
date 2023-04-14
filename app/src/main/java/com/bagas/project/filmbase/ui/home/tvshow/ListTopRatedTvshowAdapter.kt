package com.bagas.project.filmbase.ui.home.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.data.local.TopRatedTvEntity
import com.bagas.project.filmbase.data.responses.TopRatedTvshowItem
import com.bagas.project.filmbase.databinding.ItemRowTopRatedBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bumptech.glide.Glide

class ListTopRatedTvshowAdapter: ListAdapter<TopRatedTvEntity, ListTopRatedTvshowAdapter.ListTopRatedTvshowVH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTopRatedTvshowAdapter.ListTopRatedTvshowVH {
        val binding = ItemRowTopRatedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTopRatedTvshowVH(binding)
    }

    override fun onBindViewHolder(
        holder: ListTopRatedTvshowAdapter.ListTopRatedTvshowVH,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ListTopRatedTvshowVH(private val binding: ItemRowTopRatedBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(topRatedTvEntity: TopRatedTvEntity) {
            with(binding) {
                tvItemTitle.text = topRatedTvEntity.name.toString().trim()
                tvItemRating.text = topRatedTvEntity.voteAverage.toString().trim()

                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + topRatedTvEntity.posterPath)
                    .error(R.drawable.image_load_error)
                    .placeholder(R.drawable.image_loading_placeholder)
                    .into(tvItemImg)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TV_DETAIL, topRatedTvEntity.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TopRatedTvEntity> =
            object : DiffUtil.ItemCallback<TopRatedTvEntity>() {
                override fun areItemsTheSame(
                    oldItem: TopRatedTvEntity,
                    newItem: TopRatedTvEntity
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: TopRatedTvEntity,
                    newItem: TopRatedTvEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TopRatedTvshowItem?)
    }
}