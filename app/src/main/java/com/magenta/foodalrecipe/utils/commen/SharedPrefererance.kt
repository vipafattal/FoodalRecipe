package com.magenta.foodalrecipe.utils.commen

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.magenta.foodalrecipe.utils.AppMainContext.Companion.appMainContext
import java.util.*


/**
 * Created by Abed on 12/14/2017.
 */
class SharedPrefererance {
    companion object {
        val instance: SharedPrefererance by lazy { SharedPrefererance() }

        private const val DATA_KEY = "KidsLab"
        private val mPref: SharedPreferences by lazy {
            appMainContext.getSharedPreferences(
                DATA_KEY,
                Context.MODE_PRIVATE
            )
        }
        /* The private set is used
         so that a value can’t be assigned from an external class.*/

    }

    fun getDouble(key: String): Double {
        return mPref.getString(key, "0.0")!!.toDouble()
    }

    fun getInt(key: String, defValue: Int = 0): Int {
        return mPref.getInt(key, defValue)
    }

    fun getLong(key: String): Long {
        return mPref.getLong(key, Date().time)
    }

    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return mPref.getBoolean(key, defValue)
    }

    fun getStr(key: String): String {
        return mPref.getString(key, "No data")!!
    }

    fun put(key: String, `val`: String) {

        mPref.edit {
            putString(key, `val`)
        }
    }


    fun put(key: String, `val`: Int) {
        mPref.edit {
            putInt(key, `val`)
        }
    }

    fun put(key: String, `val`: Long) {
        mPref.edit {
            putLong(key, `val`)
        }
    }


    fun put(key: String, `val`: Boolean) {
        mPref.edit {
            putBoolean(key, `val`)
        }

    }


    fun put(key: String, `val`: Float) {
        mPref.edit {
            putFloat(key, `val`)
        }
    }

    /**
     *
     * Convenience method for storing doubles.
     *
     *
     *
     * There may be instances where the accuracy of a double is desired.
     *
     * SharedPreferences does not handle doubles so they have to
     *
     * cast to and from String.
     *
     *
     *
     * @param key The name of the preference to store.
     *
     * @param val The new value for the preference.
     */

    fun put(key: String, `val`: Double) {
        mPref.edit {
            putString(key, `val`.toString())
        }
    }

    fun remove(key: Int) {
        mPref.edit {
            remove(key.toString())
        }
    }

    /**
     *
     * Remove all keys from SharedPreferences.
     *
     */
    fun clear() {
        mPref.edit {
            clear()
        }
    }
}

