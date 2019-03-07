package com.codebox.lib.android.utils.screenHelpers

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue


/**
 * Defines a scale factor to use for scaling what'imageItem displayed
 */
// Set the screen width and height

internal class ScreenUtils(val mContext: Context) {

    private var dm: DisplayMetrics = mContext.resources.displayMetrics

    val screenWidth: Int = dm.widthPixels
    val screenHeight: Int = dm.heightPixels


    fun density() = dm.density

    fun sp(value: Float) = pixelsToSp(value)


    fun dp(px: Float) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
                    mContext.resources.displayMetrics)

    fun pixelsToSp(px: Float): Float {
        val scaledDensity = mContext.resources.displayMetrics.scaledDensity
        return px / scaledDensity
    }
}
