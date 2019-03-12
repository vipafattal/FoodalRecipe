package com.magenta.foodalrecipe.utils

import android.app.Application
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.utils.commen.DARK_THEME
import com.magenta.foodalrecipe.utils.commen.SharedPrefererance

/**
 * Created by Abed on 2/13/2018.
 */
class AppMainContext : Application() {
    companion object {
        var appMainContext: AppMainContext by DelegatesExt.notNullSingleValue()
            private set

        private lateinit var sharedPrefs : SharedPrefererance
        private val isDarkModeEnabled
        get() = sharedPrefs.getBoolean(DARK_THEME)
        /*     val DiInstance: AppComponent by lazy {
                 DaggerAppComponent.builder().appModule(AppModule(RecipeDatabase.getRecipeDao(), Food2ForkAPI.create())).build()
             }*/
    }

    fun getAppTheme(): Int =
        if (isDarkModeEnabled)
            R.style.AppTheme_Dark
        else
            R.style.AppTheme


    override fun onCreate() {
        super.onCreate()
        appMainContext = this
        sharedPrefs= SharedPrefererance.instance
    }
}
