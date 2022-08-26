package com.bagas.project.filmbase.ui.home.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.TopRatedTvshowItem
import com.bagas.project.filmbase.databinding.ItemRowTopRatedBinding
import com.bumptech.glide.Glide

class ListTopRatedTvshowAdapter(private val listTopRatedTvshow: List<TopRatedTvshowItem?>): RecyclerView.Adapter<ListTopRatedTvshowAdapter.ListTopRatedTvshowVH>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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
        val item = listTopRatedTvshow[position]

        holder.title.text = item?.name.toString().trim()
        holder.rating.text = item?.voteAverage.toString().trim()

        Glide.with(holder.itemView.context)
            .load(BuildConfig.IMAGE_URL + item?.posterPath)
            .into(holder.poster)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listTopRatedTvshow[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listTopRatedTvshow.size

    inner class ListTopRatedTvshowVH(binding: ItemRowTopRatedBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvItemTitle
        val rating = binding.tvItemRating
        val poster = binding.tvItemImg
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TopRatedTvshowItem?)
    }
}