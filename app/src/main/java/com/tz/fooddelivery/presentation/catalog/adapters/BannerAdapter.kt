package com.tz.fooddelivery.presentation.catalog.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tz.fooddelivery.databinding.BannerItemBinding
import com.tz.fooddelivery.domain.models.BannerItem

class BannerAdapter : ListAdapter<BannerItem, BannerAdapter.BannerViewHolder>(BannerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BannerItemBinding.inflate(layoutInflater, parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val bannerItem = getItem(position)
        holder.bind(bannerItem)
    }

    inner class BannerViewHolder(private val binding: BannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bannerItem: BannerItem) {
            binding.bannerImage.setImageResource(bannerItem.bannerImage)
        }
    }

    class BannerDiffCallback : DiffUtil.ItemCallback<BannerItem>() {
        override fun areItemsTheSame(oldItem: BannerItem, newItem: BannerItem): Boolean {
            return oldItem.bannerImage == newItem.bannerImage
        }

        override fun areContentsTheSame(oldItem: BannerItem, newItem: BannerItem): Boolean {
            return oldItem == newItem
        }
    }
}