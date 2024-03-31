package com.tz.fooddelivery.presentation.catalog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.DishesItemBinding
import com.tz.fooddelivery.domain.models.DishItem

class DishesAdapter : ListAdapter<DishItem, DishesAdapter.DishesViewHolder>(DishesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DishesItemBinding.inflate(layoutInflater, parent, false)
        return DishesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DishesViewHolder, position: Int) {
        val pizzaItem = getItem(position)
        holder.bind(pizzaItem)
    }

    inner class DishesViewHolder(private val binding: DishesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dishItem: DishItem) {
            binding.title.text = dishItem.title
            binding.description.text = dishItem.description
            binding.price.text = itemView.context.getString(R.string.price)

            Glide.with(binding.image.context)
                .load(dishItem.image)
                .into(binding.image)
        }
    }

    class DishesDiffCallback : DiffUtil.ItemCallback<DishItem>() {
        override fun areItemsTheSame(oldItem: DishItem, newItem: DishItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: DishItem, newItem: DishItem): Boolean {
            return oldItem == newItem
        }
    }
}