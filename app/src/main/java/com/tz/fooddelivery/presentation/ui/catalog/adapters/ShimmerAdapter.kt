package com.tz.fooddelivery.presentation.ui.catalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class ShimmerAdapter<T : ViewBinding>(private val layoutResId: Int) :
    RecyclerView.Adapter<ShimmerAdapter.ViewHolder>() {

    abstract class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutResId, parent, false)
        return createViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    abstract fun createViewHolder(view: View): ViewHolder
    override fun getItemCount(): Int = 10
}
