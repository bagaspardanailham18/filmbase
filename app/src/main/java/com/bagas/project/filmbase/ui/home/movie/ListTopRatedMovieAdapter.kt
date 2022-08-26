package com.bagas.project.filmbase.ui.home.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.TopRatedMoviesItem
import com.bagas.project.filmbase.databinding.ItemRowTopRatedBinding
import com.bumptech.glide.Glide

class ListTopRatedMovieAdapter(private val listTopRatedMovies: List<TopRatedMoviesItem>): RecyclerView.Adapter<ListTopRatedMovieAdapter.ListTopRatedMovieVH>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTopRatedMovieVH {
        val binding = ItemRowTopRatedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTopRatedMovieVH(binding)
    }

    override fun onBindViewHolder(
        holder: ListTopRatedMovieVH,
        position: Int
    ) {
        val item = listTopRatedMovies[position]

        holder.title.text = item.title
        holder.rating.text = item.voteAverage.toString().trim()

        Glide.with(holder.itemView.context)
            .load(BuildConfig.IMAGE_URL + item.posterPath)
            .into(holder.poster)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listTopRatedMovies[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listTopRatedMovies.size

    inner class ListTopRatedMovieVH(binding: ItemRowTopRatedBinding): RecyclerView.ViewHolder(binding.root) {
        var title = binding.tvItemTitle
        var rating = binding.tvItemRating
        var poster = binding.tvItemImg
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TopRatedMoviesItem)
    }
}