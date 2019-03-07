package com.magenta.foodalrecipe.utils

import com.codebox.kidslab.Framework.Delegation.NotNullSingleValueVar

object DelegatesExt {
    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()
}