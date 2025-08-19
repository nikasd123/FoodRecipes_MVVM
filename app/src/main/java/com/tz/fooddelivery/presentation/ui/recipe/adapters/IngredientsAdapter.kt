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
import com.tz.fooddelivery.presentation.utils.IngredientMeasureScaler

class IngredientsAdapter(
    private val onItemClick: (IngredientItem) -> Unit = {}
) : ListAdapter<IngredientItem, IngredientsAdapter.IngredientViewHolder>(IngredientDiffCallback()) {

    private var showAll = false
    private var fullList: List<IngredientItem> = emptyList()
    private var servingCount: Int = 1

    fun setServingCount(count: Int) {
        servingCount = count
        notifyDataSetChanged()
    }

    fun toggleShowAll(showAll: Boolean) {
        this.showAll = showAll
        updateDisplayedList()
    }

    override fun submitList(list: List<IngredientItem>?) {
        fullList = list ?: emptyList()
        updateDisplayedList()
    }

    private fun updateDisplayedList() {
        val displayList = if (showAll || fullList.size <= 3) fullList else fullList.take(3)
        super.submitList(displayList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = IngredientItemBinding.inflate(inflater, parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(getItem(position), servingCount)
    }

    inner class IngredientViewHolder(
        private val binding: IngredientItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IngredientItem, servings: Int) {
            with(binding) {
                ingredientText.text = item.translatedName
                ingredientMeasure.text = IngredientMeasureScaler.scale(item.translatedMeasure, servings)

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