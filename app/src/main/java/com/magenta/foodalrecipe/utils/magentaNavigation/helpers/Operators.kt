package com.magenta.foodalrecipe.utils.magentaNavigation.helpers

import android.view.ViewGroup

internal infix operator fun ViewGroup.get(index: Int) = getChildAt(index)
