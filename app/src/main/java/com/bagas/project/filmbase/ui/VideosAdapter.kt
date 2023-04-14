package com.bagas.project.filmbase.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.MovieVideoItem
import com.bagas.project.filmbase.data.responses.TvVideoItem
import com.bagas.project.filmbase.databinding.ItemRowVideosBinding
import com.bumptech.glide.Glide

class VideosAdapter(private val listMovieVideo: List<MovieVideoItem?>, private val listTvVideo: List<TvVideoItem?>): RecyclerView.Adapter<VideosAdapter.VideosVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosAdapter.VideosVH {
        val binding = ItemRowVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosVH(binding)
    }

    override fun onBindViewHolder(holder: VideosAdapter.VideosVH, position: Int) {
        if (listMovieVideo.isNotEmpty()) {
            val itemMovieVideo = listMovieVideo[position]
            holder.videoTitle.text = itemMovieVideo?.name.toString().trim()
            Glide.with(holder.itemView.context)
                .load(BuildConfig.THUMBNAIL_YOUTUBE_URL + itemMovieVideo?.key + "/maxresdefault.jpg")
                .into(holder.videoThumbnail)

            holder.itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.WATCH_YOUTUBE_URL + itemMovieVideo?.key))
                holder.itemView.context.startActivity(intent)
            }
        } else {
            val itemTvVideo = listTvVideo[position]
            holder.videoTitle.text = itemTvVideo?.name.toString().trim()
            Glide.with(holder.itemView.context)
                .load(BuildConfig.THUMBNAIL_YOUTUBE_URL + itemTvVideo?.key + "/maxresdefault.jpg")
                .into(holder.videoThumbnail)

            holder.itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.WATCH_YOUTUBE_URL + itemTvVideo?.key))
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (listMovieVideo.isNotEmpty()) {
            listMovieVideo.size
        } else {
            listTvVideo.size
        }
    }

    inner class VideosVH(binding: ItemRowVideosBinding): RecyclerView.ViewHolder(binding.root) {
        val videoTitle = binding.tvTrailerTitle
        val videoThumbnail = binding.tvThumbnail
    }

}