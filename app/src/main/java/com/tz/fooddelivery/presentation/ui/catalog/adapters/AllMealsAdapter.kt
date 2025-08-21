package com.tz.fooddelivery.presentation.ui.catalog.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tz.fooddelivery.databinding.AllDishesItemBinding
import com.tz.fooddelivery.domain.models.DishItem

class AllMealsAdapter(
    private val onItemClick: (DishItem) -> Unit,
    private val onFavoriteClick: (DishItem) -> Unit
) : ListAdapter<DishItem, AllMealsAdapter.AllDishesViewHolder>(DIFF_CALLBACK)
{

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllMealsAdapter.AllDishesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AllDishesItemBinding.inflate(layoutInflater, parent,false)
        return AllDishesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllDishesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class AllDishesViewHolder(private val binding: AllDishesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(dishItem: DishItem) {
            binding.dishName.text = dishItem.title
            binding.dishCookingTime.text = dishItem.id

            Glide.with(binding.image.context)
                .load(dishItem)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.image)

            binding.image.setOnClickListener{
                onItemClick(dishItem)
            }

            binding.favoriteIcon.isSelected = dishItem.isFavorite

            binding.favoriteIcon.setOnClickListener{
                onFavoriteClick(dishItem)
            }

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DishItem>(){
            override fun areItemsTheSame(oldItem: DishItem, newItem: DishItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DishItem, newItem: DishItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}