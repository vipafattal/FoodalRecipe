package com.magenta.foodalrecipe.utils.extensions

import android.animation.ValueAnimator
import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.magenta.foodalrecipe.R

/**
 * Created by ${User} on ${Date}
 */


fun View.putElevation(value: Float) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        elevation = value
    }
}

inline fun <T : View> T.onLongClick(crossinline block: T.() -> Unit) {
    setOnLongClickListener {
        block(this)
        return@setOnLongClickListener true
    }
}

fun ImageView.glide(url: String, isFromCache: Boolean) {

   val cacheStrategy =  if (isFromCache) DiskCacheStrategy.AUTOMATIC  else DiskCacheStrategy.NONE
    Glide.with(context).load(url)
        .diskCacheStrategy(cacheStrategy).into(this)
}

fun Chip.unChecked() {
    setChipBackgroundColorResource(R.color.disabled)
    putElevation(0f)
}

fun Chip.checked(elevation: Float) {
    setChipBackgroundColorResource(R.color.colorPrimaryDark)
    putElevation(elevation)
}

inline fun TextInputEditText.onSubmit(
    submitId: Int = EditorInfo.IME_ACTION_SEARCH,
    crossinline action: (searchKeyword: String) -> Unit
) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == submitId && !text.isNullOrBlank()) {

            action(text!!.toString())

            return@setOnEditorActionListener true
        }
        false
    }
}

fun CardView.animateElevation(animateOut: Boolean) {
    val elevation = resources.getDimension(R.dimen.toolbar_elevation)
    var endElevation = elevation
    var startElevation = 0f

    if (animateOut) {
        startElevation = elevation
        endElevation = 0f
    }
    ValueAnimator().apply {
        duration = 250
        setFloatValues(startElevation,endElevation)
        setTarget(this)
        start()
    }.addUpdateListener {
        cardElevation =  it.animatedValue as Float
    }
}