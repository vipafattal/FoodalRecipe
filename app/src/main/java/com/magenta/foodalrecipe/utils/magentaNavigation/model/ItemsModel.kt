package com.magenta.foodalrecipe.utils.magentaNavigation.model

import android.graphics.drawable.Drawable

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class ItemsModel(val title:String, @DrawableRes @ColorRes val drawable: Drawable, val itemID:Int)