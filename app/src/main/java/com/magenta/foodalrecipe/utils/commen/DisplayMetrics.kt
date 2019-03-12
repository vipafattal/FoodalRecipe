package com.codebox.lib.android.utils.screenHelpers

import android.util.DisplayMetrics
import android.util.TypedValue
import com.magenta.foodalrecipe.utils.AppMainContext.Companion.appMainContext


/**
 * Defines a scale factor to use for scaling what'imageItem displayed
 */
// Set the screen width and height

private var dm: DisplayMetrics = appMainContext.resources.displayMetrics

val screenWidth: Int = dm.widthPixels
val screenHeight: Int = dm.heightPixels


fun density() = dm.density

fun dp(px: Int): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, px.toFloat(),
        appMainContext.resources.displayMetrics
    ).toInt()

/*fun pixelsToSp(px: Float): Float {
    val scaledDensity = appResources.displayMetrics.scaledDensity
    return px / scaledDensity
}*/



