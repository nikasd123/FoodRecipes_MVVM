package com.tz.fooddelivery.presentation.ui.catalog.adapters

import android.content.Context
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
    private val clickListener: (Category?) -> Unit
) : ListAdapter<Category, FiltersAdapter.ViewHolder>(ItemDiffCallback()) {

    private var activeItem: Category? = null

    fun setSelectedCategory(category: Category?) {
        val previousActive = activeItem
        activeItem = category
        val indexesToUpdate = mutableListOf<Int>()

        currentList.forEachIndexed { index, item ->
            if (item == previousActive || item == activeItem) {
                indexesToUpdate.add(index)
            }
        }

        indexesToUpdate.forEach { index ->
            notifyItemChanged(index)
        }
    }

    inner class ViewHolder(private val binding: FiltersItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category, clickListener: (Category?) -> Unit) {
            val isActive = item == activeItem

            binding.rootCard.setOnClickListener {
                val newSelection = if (isActive) null else item
                clickListener(newSelection)
            }

            updateAppearance(isActive)
            binding.textView.text = item.category
        }

        private fun updateAppearance(isActive: Boolean) {
            val context = binding.root.context
            if (isActive) {
                binding.rootCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.background_pink))
                binding.textView.setTextColor(ContextCompat.getColor(context, R.color.elements_pink))
                binding.textView.alpha = 1.0f
            } else {
                binding.rootCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                binding.textView.setTextColor(ContextCompat.getColor(context, R.color.black))
                binding.textView.alpha = 0.3f
            }
        }

        private fun Float.dpToPx(context: Context): Float =
            this * context.resources.displayMetrics.density
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
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Category, newItem: Category): Any? {
            return if (oldItem.isActive != newItem.isActive) true else null
        }
    }
}