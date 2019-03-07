package com.magenta.foodalrecipe.utils.commen.extensions

import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.magenta.foodalrecipe.R

/**
 * Created by ${User} on ${Date}
 */

fun ImageView.glide(url: String) {
    Glide.with(this).load(url).into(this)
}

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

fun Chip.unChecked() {
    setChipBackgroundColorResource(R.color.disabled)
    putElevation(0f)
}

fun Chip.checked(elevation: Float) {
    setChipBackgroundColorResource(R.color.colorAccent)
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