package com.bagas.project.filmbase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.MovieSearchItem
import com.bagas.project.filmbase.databinding.ItemRowTrendingBinding
import com.bumptech.glide.Glide

class ListSearchAdapter(private val listSearchData: List<MovieSearchItem?>): RecyclerView.Adapter<ListSearchAdapter.ListSearchVH>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListSearchAdapter.ListSearchVH {
        val binding = ItemRowTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListSearchVH(binding)
    }

    override fun onBindViewHolder(holder: ListSearchAdapter.ListSearchVH, position: Int) {
        val item = listSearchData[position]

        holder.title.text = item?.title.toString()
        holder.rating.text = item?.voteAverage?.toString()?.trim()

        Glide.with(holder.itemView.context)
            .load(BuildConfig.IMAGE_URL + item?.posterPath)
            .into(holder.poster)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listSearchData[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listSearchData.size

    inner class ListSearchVH(binding: ItemRowTrendingBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvItemTitle
        val rating = binding.tvItemRating
        val poster = binding.tvItemImg
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MovieSearchItem?)
    }
}