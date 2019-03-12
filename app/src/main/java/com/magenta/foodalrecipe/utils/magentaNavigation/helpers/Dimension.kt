package com.magenta.foodalrecipe.utils.magentaNavigation.helpers

import android.content.res.TypedArray

internal fun TypedArray.getDim(indexName: Int, defaultValue: Number): Number {
    val defaultWidthValue = -9876121f
    val dimension = this.getDimension(indexName, defaultWidthValue)
    return if (dimension == defaultWidthValue) defaultValue
    else dimension
}