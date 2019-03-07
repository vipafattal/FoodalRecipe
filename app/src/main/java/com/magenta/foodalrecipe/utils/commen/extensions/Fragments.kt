package com.magenta.foodalrecipe.utils.commen.extensions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Created by ${User} on ${Date}
 */

val AppCompatActivity.transaction
    get() = supportFragmentManager.beginTransaction()


fun AppCompatActivity.replaceFragment(fragment: Fragment, @IdRes viewID: Int, tag: String? = null) {
    supportFragmentManager.transaction {
        replace(viewID, fragment, tag)
    }
}

inline fun FragmentManager.transaction(
    commit: Boolean = true,
    action: FragmentTransaction.() -> FragmentTransaction
):FragmentTransaction {
    val transaction = beginTransaction()
    if (commit)
        transaction.action().commit()
    else
        transaction.action()
    return transaction
}