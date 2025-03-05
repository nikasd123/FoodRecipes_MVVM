package com.tz.fooddelivery.presentation.ui.catalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tz.fooddelivery.R
import com.tz.fooddelivery.databinding.ShimmerDishesItemBinding
import com.tz.fooddelivery.databinding.ShimmerFilterItemBinding

abstract class BaseShimmerAdapter<B : ViewBinding>(
    private val layoutRes: Int
) : RecyclerView.Adapter<BaseShimmerAdapter.ViewHolder<B>>() {

    abstract fun createBinding(view: View): B

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutRes(), parent, false)
        return ViewHolder(createBinding(view))
    }

    private fun getLayoutRes(): Int = layoutRes

    class ViewHolder<B : ViewBinding>(binding: B) : RecyclerView.ViewHolder(binding.root)
}

class ShimmerFiltersAdapter : BaseShimmerAdapter<ShimmerFilterItemBinding>(R.layout.shimmer_filter_item) {
    override fun createBinding(view: View) = ShimmerFilterItemBinding.bind(view)
    override fun getItemCount(): Int = 6
    override fun onBindViewHolder(holder: ViewHolder<ShimmerFilterItemBinding>, position: Int) {}
}

class ShimmerDishesAdapter : BaseShimmerAdapter<ShimmerDishesItemBinding>(R.layout.shimmer_dishes_item) {
    override fun createBinding(view: View) = ShimmerDishesItemBinding.bind(view)
    override fun getItemCount(): Int = 5
    override fun onBindViewHolder(holder: ViewHolder<ShimmerDishesItemBinding>, position: Int) {}
}
