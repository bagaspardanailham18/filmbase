package com.bagas.project.filmbase.ui.home.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.data.local.entities.TopRatedMovieEntity
import com.bagas.project.filmbase.databinding.ItemRowTopRatedBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bumptech.glide.Glide

class ListTopRatedMovieAdapter: ListAdapter<TopRatedMovieEntity, ListTopRatedMovieAdapter.ListTopRatedMovieVH>(DIFF_CALLBACK
) {

//    private lateinit var onItemClickCallback: OnItemClickCallback
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

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
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ListTopRatedMovieVH(private val binding: ItemRowTopRatedBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(topRatedMovie: TopRatedMovieEntity) {
            with(binding) {
                tvItemTitle.text = topRatedMovie.title
                tvItemRating.text = topRatedMovie.voteAverage.toString().trim()

                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + topRatedMovie.posterPath)
                    .error(R.drawable.image_load_error)
                    .placeholder(R.drawable.image_loading_placeholder)
                    .into(tvItemImg)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE_DETAIL, topRatedMovie.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TopRatedMovieEntity> =
            object : DiffUtil.ItemCallback<TopRatedMovieEntity>() {
                override fun areItemsTheSame(
                    oldItem: TopRatedMovieEntity,
                    newItem: TopRatedMovieEntity
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: TopRatedMovieEntity,
                    newItem: TopRatedMovieEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

//    interface OnItemClickCallback {
//        fun onItemClicked(data: TopRatedMoviesItem)
//    }
}