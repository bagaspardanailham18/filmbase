package com.bagas.project.filmbase.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.TrendingMoviesItem
import com.bagas.project.filmbase.data.responses.TrendingTvshowItem
import com.bagas.project.filmbase.databinding.ItemRowTopRatedBinding
import com.bagas.project.filmbase.databinding.ItemRowTrendingBinding
import com.bumptech.glide.Glide

class ListTrendingAdapter(private val listTrending: List<TrendingMoviesItem?>, private val listTrendingTvshow: List<TrendingTvshowItem?>): RecyclerView.Adapter<ListTrendingAdapter.ListTrendingVH>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTrendingAdapter.ListTrendingVH {
        val binding = ItemRowTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTrendingVH(binding)
    }

    override fun onBindViewHolder(holder: ListTrendingAdapter.ListTrendingVH, position: Int) {

        if (listTrending != emptyList<TrendingMoviesItem>()) {
            val item = listTrending[position]
            holder.title.text = item?.title.toString().trim()
            holder.rating.text = item?.voteAverage.toString().trim()
            Glide.with(holder.itemView.context)
                .load(BuildConfig.IMAGE_URL + item?.posterPath)
                .into(holder.poster)

            holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listTrending[holder.adapterPosition]!!, null) }
        } else {
            val itemTvshow = listTrendingTvshow[position]
            holder.title.text = itemTvshow?.name.toString().trim()
            holder.rating.text = itemTvshow?.voteAverage.toString().trim()
            Glide.with(holder.itemView.context)
                .load(BuildConfig.IMAGE_URL + itemTvshow?.posterPath)
                .into(holder.poster)
            holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(null, listTrendingTvshow[holder.adapterPosition]!!, ) }
        }

    }

    override fun getItemCount(): Int {
        return if (listTrending != emptyList<TrendingMoviesItem>()) {
            listTrending.size
        } else {
            listTrendingTvshow.size
        }
    }

    inner class ListTrendingVH(binding: ItemRowTrendingBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvItemTitle
        val rating = binding.tvItemRating
        val poster = binding.tvItemImg
    }

    interface OnItemClickCallback {
        fun onItemClicked(dataTrendingMovie: TrendingMoviesItem?, dataTrendingTv: TrendingTvshowItem?)
    }
}