package com.magenta.foodalrecipe.utils.commen.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Created by ${User} on ${Date}
 */

fun Context.errorNotifier(userMsg: String, loggerMsg: String, duration: Int = Toast.LENGTH_LONG) {
    Log.d(userMsg , loggerMsg)
    Toast.makeText(this, userMsg, duration).show()
}
