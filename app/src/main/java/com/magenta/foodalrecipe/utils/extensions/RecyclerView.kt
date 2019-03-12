package com.magenta.foodalrecipe.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ${User} on ${Date}
 */

inline fun RecyclerView.onScroll(crossinline action: (dx: Int, dy: Int) -> Unit) {

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            action(dx, dy)
        }
    })
}

fun ViewGroup.inflater(@LayoutRes id: Int): View =
    LayoutInflater.from(this.context).inflate(id, this, false)

fun ViewGroup.inflater(@LayoutRes id: Int, tag: Any): View {
    val view = inflater(id)
    view.tag = tag
    return view
}
