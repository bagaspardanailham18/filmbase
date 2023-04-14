package com.bagas.project.filmbase.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.UpcomingMoviesItem
import com.bagas.project.filmbase.databinding.ItemRowSeeAllBinding
import com.bumptech.glide.Glide

class SeeAllAdapter(private val listSeeAll: List<UpcomingMoviesItem?>): RecyclerView.Adapter<SeeAllAdapter.SeeAllVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeAllAdapter.SeeAllVH {
        val binding = ItemRowSeeAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeeAllVH(binding)
    }

    override fun onBindViewHolder(holder: SeeAllAdapter.SeeAllVH, position: Int) {
        val item = listSeeAll[position]

        holder.tvTitle.text = item?.title
        holder.tvReleaseDate.text = item?.releaseDate
//        holder.tvGenre.text = item?.genreIds?.get(0).toString()
        holder.tvRates.text = item?.voteAverage.toString().trim()

        Glide.with(holder.itemView.context)
            .load(BuildConfig.IMAGE_URL + item?.posterPath)
            .into(holder.tvImage)
    }

    override fun getItemCount(): Int = listSeeAll.size

    inner class SeeAllVH(binding: ItemRowSeeAllBinding): RecyclerView.ViewHolder(binding.root){
        val tvImage = binding.tvItemImg
        val tvTitle = binding.tvItemTitle
        val tvReleaseDate = binding.tvItemReleaseDate
        val tvRates = binding.tvItemRating
        val tvGenre = binding.tvItemGenre
    }
}