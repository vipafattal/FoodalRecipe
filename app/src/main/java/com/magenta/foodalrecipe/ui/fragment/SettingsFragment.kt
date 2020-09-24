package com.magenta.foodalrecipe.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.bumptech.glide.Glide
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.framework.Injection
import com.magenta.foodalrecipe.ui.MainActivity
import com.magenta.foodalrecipe.ui.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.utils.commen.*
import com.magenta.foodalrecipe.utils.extensions.onPreferencesClick
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        newValue as Boolean
        when (preference.key) {
            //Theme
            getString(R.string.dark_theme) -> {
                sharedPreference.put(DARK_THEME, newValue)
                activity?.recreate()
            }
            //Cache settings
            getString(R.string.save_offline) -> sharedPreference.put(SAVE_DATA, newValue)
            getString(R.string.cache_images) -> sharedPreference.put(CACHE_IMAGES, newValue)
            //Search settings
            getString(R.string.search_offline) -> sharedPreference.put(OFFLINE_SEARCH, newValue)
            getString(R.string.store_search_result) -> sharedPreference.put(STORE_SEARCH_RESULT, newValue)
        }
        return true
    }

    private val sharedPreference = SharedPrefererance.instance

    private val job = SupervisorJob()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + job)
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey)
        val viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(RecipeRepositoriesViewModel::class.java)

        initChangePreference()

        onPreferencesClick(R.string.erase_database, "Saved Data Cleared") {
            viewModel.deleteByFavoriteState(false)
            val i = context?.packageManager!!.getLaunchIntentForPackage(requireContext().packageName)
            i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }

        onPreferencesClick(R.string.erase_images_cache, "Images Cache Cleared", coroutineScope) {
            Glide.get(requireContext()).clearDiskCache()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).magentaNav!!.animate()
            .setDuration(400)
            .translationY(0f)
            .start()
    }

    private fun initChangePreference() {
        findPreference<CheckBoxPreference>(getString(R.string.dark_theme))!!.onPreferenceChangeListener = this
        findPreference<CheckBoxPreference>(getString(R.string.save_offline))!!.onPreferenceChangeListener = this
        findPreference<CheckBoxPreference>(getString(R.string.cache_images))!!.onPreferenceChangeListener = this
        findPreference<SwitchPreference>(getString(R.string.search_offline))!!.onPreferenceChangeListener = this
        findPreference<SwitchPreference>(getString(R.string.store_search_result))!!.onPreferenceChangeListener = this
    }

    companion object {
        const val TAG = "Settings-Fragment"
    }
}
