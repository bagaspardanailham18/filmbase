package com.bagas.project.filmbase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.TvshowSearchItem
import com.bagas.project.filmbase.databinding.ItemRowTrendingBinding
import com.bumptech.glide.Glide

class ListSearchTvAdapter(private val listSeachTvData: List<TvshowSearchItem?>): RecyclerView.Adapter<ListSearchTvAdapter.ListSearchTvVH>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListSearchTvAdapter.ListSearchTvVH {
        val binding = ItemRowTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListSearchTvVH(binding)
    }

    override fun onBindViewHolder(holder: ListSearchTvAdapter.ListSearchTvVH, position: Int) {
        val item = listSeachTvData[position]

        holder.title.text = item?.name.toString().trim()
        holder.rating.text = item?.voteAverage.toString().trim()

        Glide.with(holder.itemView.context)
            .load(BuildConfig.IMAGE_URL + item?.posterPath)
            .into(holder.poster)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listSeachTvData[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listSeachTvData.size

    inner class ListSearchTvVH(binding: ItemRowTrendingBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvItemTitle
        val rating = binding.tvItemRating
        val poster = binding.tvItemImg
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvshowSearchItem?)
    }

}