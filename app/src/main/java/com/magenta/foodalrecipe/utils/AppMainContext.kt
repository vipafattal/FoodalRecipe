package com.magenta.foodalrecipe.utils

import android.app.Application

/**
 * Created by Abed on 2/13/2018.
 */
class AppContext : Application() {
    companion object {
        var appMainContext: AppContext by DelegatesExt.notNullSingleValue()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appMainContext = this
    }
}
