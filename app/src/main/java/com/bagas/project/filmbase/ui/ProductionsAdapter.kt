package com.bagas.project.filmbase.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagas.project.filmbase.BuildConfig
import com.bagas.project.filmbase.data.responses.MovieProductionCompaniesItem
import com.bagas.project.filmbase.data.responses.TvProductionCompaniesItem
import com.bagas.project.filmbase.databinding.ItemRowProductionBinding
import com.bumptech.glide.Glide

class ProductionsAdapter(private val listMovieProduction: List<MovieProductionCompaniesItem?>, private val listTvProduction: List<TvProductionCompaniesItem?>): RecyclerView.Adapter<ProductionsAdapter.ProductionsVH>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductionsAdapter.ProductionsVH {
        val binding = ItemRowProductionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductionsVH(binding)
    }

    override fun onBindViewHolder(holder: ProductionsAdapter.ProductionsVH, position: Int) {
        if (listMovieProduction.isNotEmpty()) {
            val movieProductionsItem = listMovieProduction[position]
            holder.companyName.text = movieProductionsItem?.name.toString().trim()
            Glide.with(holder.itemView.context)
                .load(BuildConfig.IMAGE_URL + movieProductionsItem?.logoPath)
                .into(holder.companyIcon)
        } else {
            val tvProductionsItem = listTvProduction[position]
            holder.companyName.text = tvProductionsItem?.name.toString().trim()
            Glide.with(holder.itemView.context)
                .load(BuildConfig.IMAGE_URL + tvProductionsItem?.logoPath)
                .into(holder.companyIcon)
        }
    }

    override fun getItemCount(): Int {
        return if (listMovieProduction.isNotEmpty()) {
            listMovieProduction.size
        } else {
            listTvProduction.size
        }
    }

    inner class ProductionsVH(binding: ItemRowProductionBinding): RecyclerView.ViewHolder(binding.root) {
        val companyName = binding.tvCompanyName
        val companyIcon = binding.tvCompanyIcon
    }
}