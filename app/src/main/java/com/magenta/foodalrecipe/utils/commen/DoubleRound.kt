package com.magenta.foodalrecipe.utils.commen

fun Double.round(precision: Int): Double {
        val scale = Math.pow(10.0, precision.toDouble()).toInt()
        return Math.round(this * scale).toDouble() / scale
    }