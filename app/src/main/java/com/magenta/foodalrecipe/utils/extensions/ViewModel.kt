package com.magenta.foodalrecipe.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

/**
 * Created by ${User} on ${Date}
 */
inline fun <T> LiveData<T>.observer(fragment: Fragment, crossinline doOnObserver: (T) -> Unit) =
    observe(fragment.activity!!, Observer {
        doOnObserver(it)
    })

inline fun <T> LiveData<T>.observer(activity: AppCompatActivity, crossinline doOnObserver: (T) -> Unit) =
    observe(activity, Observer {
        doOnObserver(it)
    })

fun <T: ViewModel> Fragment.viewModelOf(viewModelClass: Class<T>)=
    ViewModelProviders.of(activity!!).get(viewModelClass)


fun <T:ViewModel> AppCompatActivity.viewModelOf(viewModelClass: Class<T>)=
    ViewModelProviders.of(this).get(viewModelClass)
