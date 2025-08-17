package com.tz.fooddelivery.presentation.ui.recipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tz.fooddelivery.databinding.InfoItemBinding

class ShortInfoAdapter : ListAdapter<ShortInfoItem, ShortInfoAdapter.ShortInfoViewHolder>(ShortInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InfoItemBinding.inflate(inflater, parent, false)
        return ShortInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShortInfoViewHolder(
        private val binding: InfoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShortInfoItem) {
            binding.tvInfo.text = item.value
        }
    }

    private class ShortInfoDiffCallback : DiffUtil.ItemCallback<ShortInfoItem>() {
        override fun areItemsTheSame(oldItem: ShortInfoItem, newItem: ShortInfoItem): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: ShortInfoItem, newItem: ShortInfoItem): Boolean {
            return oldItem == newItem
        }
    }
}