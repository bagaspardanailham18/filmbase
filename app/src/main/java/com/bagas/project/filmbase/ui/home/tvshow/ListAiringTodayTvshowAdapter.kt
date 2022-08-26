package com.bagas.project.filmbase.ui.home.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.AiringTodayTvshowItem
import com.bagas.project.filmbase.databinding.ItemRowUpcomingBinding
import com.bumptech.glide.Glide

class ListAiringTodayTvshowAdapter(private val listAiringTodayTvshow: List<AiringTodayTvshowItem?>): RecyclerView.Adapter<ListAiringTodayTvshowAdapter.ListAiringTodayTvshowVH>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAiringTodayTvshowVH {
        val binding = ItemRowUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListAiringTodayTvshowVH(binding)
    }

    override fun onBindViewHolder(holder: ListAiringTodayTvshowVH, position: Int) {
        val item = listAiringTodayTvshow[position]

        holder.title.text = item?.name.toString().trim()
        holder.rating.text = item?.voteAverage.toString().trim()

        Glide.with(holder.itemView.context)
            .load(BuildConfig.IMAGE_URL + item?.posterPath)
            .into(holder.poster)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listAiringTodayTvshow[holder.adapterPosition]!!) }
    }

    override fun getItemCount(): Int = listAiringTodayTvshow.size

    inner class ListAiringTodayTvshowVH(binding: ItemRowUpcomingBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvItemTitle
        val rating = binding.tvItemRating
        val poster = binding.tvItemImg
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: AiringTodayTvshowItem)
    }

}