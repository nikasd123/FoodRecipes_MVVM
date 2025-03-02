package com.tz.fooddelivery.presentation.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

internal fun setupLinearRecyclerViewWithShimmer(
    recyclerView: RecyclerView,
    shimmerRecyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    shimmerAdapter: RecyclerView.Adapter<*>,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) {
    val context = recyclerView.context

    recyclerView.layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
    recyclerView.adapter = adapter

    shimmerRecyclerView.layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
    shimmerRecyclerView.adapter = shimmerAdapter
}

internal fun setupLinearRecyclerView(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) {
    recyclerView.layoutManager = LinearLayoutManager(
        recyclerView.context,
        orientation,
        reverseLayout
    )
    recyclerView.adapter = adapter
}