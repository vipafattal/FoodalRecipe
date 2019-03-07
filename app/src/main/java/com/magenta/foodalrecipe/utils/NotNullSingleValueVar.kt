package com.codebox.kidslab.Framework.Delegation

import kotlin.reflect.KProperty

/**
 * Created by Abed on 2/13/2018.
 */


class NotNullSingleValueVar<T> {
    private var value: T? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        //usually throw an IllegalStateException whenever I catch something that "cannot possibly happen"
        // and there is no other more-descriptive exception type that I can throw.
        return value ?: throw IllegalStateException("${property.name} not initialized")
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("${property.name} already initialized")
    }
}