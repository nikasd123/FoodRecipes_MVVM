package com.tz.fooddelivery.presentation.catalog.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.FiltersItemBinding
import com.tz.fooddelivery.domain.models.Category

class FiltersAdapter(
    private val clickListener: (Category) -> Unit
) : ListAdapter<Category, FiltersAdapter.ViewHolder>(ItemDiffCallback()) {

    private var activeItem: Category? = null

    inner class ViewHolder(private val binding: FiltersItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category, clickListener: (Category) -> Unit) {
            binding.rootCard.setOnClickListener {
                activeItem?.isActive = false
                item.isActive = true
                activeItem = item
                clickListener(item)
                notifyDataSetChanged()
            }
            binding.textView.text = item.category
            val context = binding.root.context
            if (item.isActive) {
                binding.rootCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background_pink))
                binding.textView.setTextColor(ContextCompat.getColor(context, R.color.elements_pink))
                binding.textView.alpha = 1.0f
            } else {
                binding.rootCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                binding.textView.setTextColor(ContextCompat.getColor(context, R.color.black))
                binding.textView.alpha = 0.3f
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FiltersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}