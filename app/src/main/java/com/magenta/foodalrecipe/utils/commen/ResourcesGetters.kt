package com.magenta.foodalrecipe.utils.commen

import android.content.Context
import androidx.core.content.ContextCompat
import com.magenta.foodalrecipe.utils.AppMainContext


fun Colour(colorID: Int, context: Context = AppMainContext.appMainContext): Int = ContextCompat.getColor(context, colorID)

fun Stringer(stringID: Int, mContext: Context = AppMainContext.appMainContext) = mContext.resources.getString(stringID)

fun Image(drawableImg: Int, context: Context = AppMainContext.appMainContext) =
    ContextCompat.getDrawable(context, drawableImg)

/**
 * @resourcesID is string resources
 *  arrayOfStrings(R.string.appName,R.string.Welcome,R.string.Login)
 */
fun arrayOfStrings(vararg resourcesID: Int): List<String> {
    val stringsArray = ArrayList<String>()
    resourcesID.forEach {
        stringsArray.add(Stringer(it))
    }
    return stringsArray
}

fun arrayOfColor(vararg colorsID: Int): List<Int> {
    val colorsArray = ArrayList<Int>(colorsID.size)
    colorsID.forEach {
        colorsArray.add(Colour(it))
    }
    return colorsArray
}



