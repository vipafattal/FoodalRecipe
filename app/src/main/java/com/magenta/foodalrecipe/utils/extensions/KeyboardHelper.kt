package com.magenta.foodalrecipe.utils.extensions

import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Created by Abed on 3/12/2018.
 */

fun AppCompatActivity.hideKeyboard() {
    val inputMethodManager =
        this.getSystemService(androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    val inputMethodManager =
        context?.getSystemService(androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(activity?.window!!.decorView.rootView.windowToken, 0)
}

fun AppCompatActivity.showKeyboard() {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
}

fun Fragment.showKeyboard() {
    activity?.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}

private fun EditText.showKeyboardOn() {
    requestFocus()
    callOnClick()
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}
