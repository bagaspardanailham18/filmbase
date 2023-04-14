package com.bagas.project.filmbase.ui.home.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.data.local.entities.UpcomingMovieEntity
import com.bagas.project.filmbase.data.responses.UpcomingMoviesItem
import com.bagas.project.filmbase.databinding.ItemRowUpcomingBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bumptech.glide.Glide

class ListUpcomingMovieAdapter: ListAdapter<UpcomingMovieEntity, ListUpcomingMovieAdapter.ListUpcomingMovieVH>(DIFF_CALLBACK) {

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
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ListUpcomingMovieVH(private val binding: ItemRowUpcomingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(upcomingMovie: UpcomingMovieEntity) {
            with(binding) {
                tvItemTitle.text = upcomingMovie.title.toString().trim()
                tvItemRating.text = upcomingMovie.voteAverage.toString().trim()
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + upcomingMovie.posterPath)
                    .error(R.drawable.image_load_error)
                    .placeholder(R.drawable.image_loading_placeholder)
                    .into(tvItemImg)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE_DETAIL, upcomingMovie.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UpcomingMovieEntity> =
            object : DiffUtil.ItemCallback<UpcomingMovieEntity>() {
                override fun areItemsTheSame(
                    oldItem: UpcomingMovieEntity,
                    newItem: UpcomingMovieEntity
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: UpcomingMovieEntity,
                    newItem: UpcomingMovieEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UpcomingMoviesItem)
    }
}