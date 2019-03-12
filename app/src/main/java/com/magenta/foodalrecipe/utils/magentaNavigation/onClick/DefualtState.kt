package com.magenta.foodalrecipe.utils.magentaNavigation.onClick

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.magenta.foodalrecipe.utils.magentaNavigation.helpers.get

internal fun ViewGroup.loadDefaultActionState(clickedNav: CardView, defaultCardElevation: Float, accentDefaultColor: Int, defaultNavColor: Int) {
    for (index in 0 until childCount) {
        val parentClickedView = getChildAt(index) as LinearLayout
        val cardNav = parentClickedView[0] as CardView

        if (cardNav != clickedNav)
            parentClickedView.defaultState(defaultCardElevation, accentDefaultColor, defaultNavColor)
    }
}

private fun ViewGroup.defaultState(defaultCardElevation: Float, accentDefaultColor: Int, defaultNavColor: Int) {
    val cardView = this[0] as CardView
    val textView = this[1] as TextView
    val imgView = (cardView[0] as RelativeLayout)[0] as ImageView
    cardView.cardElevation = defaultCardElevation
    textView.setTextColor(accentDefaultColor)
    imgView.setColorFilter(accentDefaultColor)
    cardView.setCardBackgroundColor(defaultNavColor)
}