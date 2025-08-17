package com.tz.fooddelivery.presentation.ui.catalog.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tz.fooddelivery.databinding.DishesItemBinding
import com.tz.fooddelivery.domain.models.DishItem

class MealsAdapter(
    private val onItemClick: (DishItem) -> Unit
) : ListAdapter<DishItem, MealsAdapter.DishesViewHolder>(DIFF_CALLBACK) {

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
            binding.dishName.text = dishItem.title
            binding.dishCookingTime.text = dishItem.id

            Glide.with(binding.image.context)
                .load(dishItem.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.image)

            itemView.rootView.setOnClickListener {
                onItemClick(dishItem)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DishItem>() {
            override fun areItemsTheSame(oldItem: DishItem, newItem: DishItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DishItem, newItem: DishItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}