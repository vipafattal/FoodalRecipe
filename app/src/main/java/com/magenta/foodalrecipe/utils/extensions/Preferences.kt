package com.magenta.foodalrecipe.utils.extensions

import androidx.annotation.StringRes
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.magenta.foodalrecipe.utils.commen.unitFun
import com.magenta.foodalrecipe.utils.showSnackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by ${User} on ${Date}
 */

inline fun PreferenceFragmentCompat.onPreferencesClick(
    @StringRes key: Int, msg: String,
    crossinline doOnClick: unitFun
) {
    findPreference<Preference>(getString(key))!!.setOnPreferenceClickListener {
        doOnClick()
        view?.showSnackbar(msg)
        true
    }
}

inline fun PreferenceFragmentCompat.onPreferencesClick(
    @StringRes key: Int,
    msg: String,
    coroutineScope: CoroutineScope,
    crossinline doOnClick: suspend () -> Unit
) {
    findPreference<Preference>(getString(key))!!.setOnPreferenceClickListener {
        coroutineScope.launch { doOnClick() }
        view?.showSnackbar(msg)
        true
    }

}