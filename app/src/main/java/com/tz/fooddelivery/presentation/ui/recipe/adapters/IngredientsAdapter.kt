package com.tz.fooddelivery.presentation.ui.recipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tz.fooddelivery.databinding.IngredientItemBinding
import com.tz.fooddelivery.domain.models.IngredientItem

class IngredientsAdapter(
    private val onItemClick: (IngredientItem) -> Unit = {}
) : ListAdapter<IngredientItem, IngredientsAdapter.IngredientViewHolder>(IngredientDiffCallback()) {

    private var showAllIngredients = false

    fun toggleShowAll(showAll: Boolean) {
        showAllIngredients = showAll
        submitList(currentList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = IngredientItemBinding.inflate(inflater, parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<IngredientItem>?) {
        super.submitList(list)
    }

    inner class IngredientViewHolder(
        private val binding: IngredientItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IngredientItem) {
            with(binding) {
                ingredientText.text = item.translatedName
                ingredientMeasure.text = item.translatedMeasure
                Glide.with(ingredientImage.context)
                    .load(item.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ingredientImage)

                root.setOnClickListener { onItemClick(item) }
            }
        }
    }

    private class IngredientDiffCallback : DiffUtil.ItemCallback<IngredientItem>() {
        override fun areItemsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
            return oldItem.originalName == newItem.originalName
        }

        override fun areContentsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
            return oldItem == newItem
        }
    }
}