package com.magenta.foodalrecipe.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.utils.commen.Colour
import com.magenta.foodalrecipe.utils.commen.Image


fun View.showSnackbar(
    msg: String,
    duration: Int = Snackbar.LENGTH_LONG
) {

    val sn = Snackbar.make(this, msg, duration)

    sn.view.background = Image(R.drawable.bg_snackbar)
    sn.setActionTextColor(Colour(R.color.colorPrimaryDark))
    sn.setAction("dismiss") { sn.dismiss() }
    sn.show()

}




