package com.tz.fooddelivery.presentation.common

import android.view.View

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