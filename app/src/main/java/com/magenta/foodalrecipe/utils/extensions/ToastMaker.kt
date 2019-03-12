package com.magenta.foodalrecipe.utils.extensions

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

fun Context.toastLong(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}
fun Context.toastShort(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}
