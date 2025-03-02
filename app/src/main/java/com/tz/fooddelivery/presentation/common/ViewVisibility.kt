package com.tz.fooddelivery.presentation.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal fun View.visible(){
    visibility = View.VISIBLE
}

internal fun View.gone(){
    visibility = View.GONE
}

fun setViewsVisibility(vararg views: Pair<View, Boolean>) {
    views.forEach { (view, isVisible) ->
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}

internal fun RecyclerView.showRecyclerView(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}