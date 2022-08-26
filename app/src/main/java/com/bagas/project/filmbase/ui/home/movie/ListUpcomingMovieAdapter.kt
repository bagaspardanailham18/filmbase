package com.bagas.project.filmbase.ui.home.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.UpcomingMoviesItem
import com.bagas.project.filmbase.databinding.ItemRowUpcomingBinding
import com.bumptech.glide.Glide

class ListUpcomingMovieAdapter(private val listUpcomingMovies: List<UpcomingMoviesItem?>): RecyclerView.Adapter<ListUpcomingMovieAdapter.ListUpcomingMovieVH>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListUpcomingMovieVH {
        val binding = ItemRowUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUpcomingMovieVH(binding)
    }

    override fun onBindViewHolder(
        holder: ListUpcomingMovieVH,
        position: Int
    ) {
        val item = listUpcomingMovies[position]

        Log.d("Data", item.toString())

        holder.title.text = item?.title.toString().trim()
        holder.rating.text = item?.voteAverage.toString().trim()
        Glide.with(holder.itemView.context)
            .load(BuildConfig.IMAGE_URL + item?.posterPath)
            .into(holder.poster)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUpcomingMovies[holder.adapterPosition]!!) }
    }

    override fun getItemCount(): Int = listUpcomingMovies.size

    inner class ListUpcomingMovieVH(binding: ItemRowUpcomingBinding): RecyclerView.ViewHolder(binding.root) {
        var title = binding.tvItemTitle
        var rating = binding.tvItemRating
        var poster = binding.tvItemImg
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UpcomingMoviesItem)
    }
}