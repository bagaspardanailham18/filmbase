package com.bagas.project.filmbase.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.local.entities.FavoriteMovieEntity
import com.bagas.project.filmbase.databinding.ItemRowTrendingBinding
import com.bagas.project.filmbase.ui.DetailActivity
import com.bumptech.glide.Glide

class FavoritedMovieAdapter: ListAdapter<FavoriteMovieEntity, FavoritedMovieAdapter.FavoritedMovieVH>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritedMovieAdapter.FavoritedMovieVH {
        val binding = ItemRowTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritedMovieVH(binding)
    }

    override fun onBindViewHolder(holder: FavoritedMovieAdapter.FavoritedMovieVH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class FavoritedMovieVH(private val binding: ItemRowTrendingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteMovie: FavoriteMovieEntity) {
            with(binding) {
                tvItemTitle.text = favoriteMovie.title
                tvItemRating.text = favoriteMovie.voteAverage.toString().trim()

                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGE_URL + favoriteMovie.posterPath)
                    .into(tvItemImg)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE_DETAIL, favoriteMovie.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK:DiffUtil.ItemCallback<FavoriteMovieEntity> =
            object : DiffUtil.ItemCallback<FavoriteMovieEntity>() {
                override fun areItemsTheSame(
                    oldItem: FavoriteMovieEntity,
                    newItem: FavoriteMovieEntity
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: FavoriteMovieEntity,
                    newItem: FavoriteMovieEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}