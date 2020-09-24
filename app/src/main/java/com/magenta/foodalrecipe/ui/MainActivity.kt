package com.magenta.foodalrecipe.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.framework.Injection
import com.magenta.foodalrecipe.ui.ViewModel.NavigationViewModel
import com.magenta.foodalrecipe.ui.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.ui.fragment.FavoriteFragment
import com.magenta.foodalrecipe.ui.fragment.HomeFragment
import com.magenta.foodalrecipe.ui.fragment.SearchFragment
import com.magenta.foodalrecipe.ui.fragment.SettingsFragment
import com.magenta.foodalrecipe.utils.AppMainContext.Companion.appMainContext
import com.magenta.foodalrecipe.utils.commen.SAVE_DATA
import com.magenta.foodalrecipe.utils.commen.SharedPrefererance
import com.magenta.foodalrecipe.utils.commen.onBackPressedFragment
import com.magenta.foodalrecipe.utils.extensions.transaction
import com.magenta.foodalrecipe.utils.extensions.viewModelOf
import com.magenta.foodalrecipe.utils.magentaNavigation.onClick.OnNavItemClicked
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnNavItemClicked {

    var currentViewFragment: Fragment = HomeFragment()
    private set

    private val sharedPreferences = SharedPrefererance.instance
    override fun onMagentaNavItemClicked(id: Int, position: Int) {
        navigationViewModel.modifyPosition(position)
        when (position) {
            0 -> updateCurrentFragment(HomeFragment(), HomeFragment.TAG)
            1 -> updateCurrentFragment(SearchFragment(), SearchFragment.TAG, 1)
            2 -> updateCurrentFragment(FavoriteFragment(), FavoriteFragment.TAG)
            3 -> updateCurrentFragment(SettingsFragment(), SettingsFragment.TAG)
        }
    }

    private lateinit var navigationViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(appMainContext.getAppTheme())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationViewModel = viewModelOf(NavigationViewModel::class.java)

        if (savedInstanceState == null) {
            magentaNav.callOnClickNavigation(navigationViewModel.currentPosition())
            if (!sharedPreferences.getBoolean(SAVE_DATA, true))
                retrieveMainViewModel().deleteByFavoriteState(false)
        } else {
            magentaNav.callOnClickNavigation(navigationViewModel.currentPosition())
            supportFragmentManager.findFragmentByTag(currentViewFragment.tag)
        }
    }

    fun updateCurrentFragment(fragment: Fragment, tag: String, addBackStackNull: Int = 0) {
        currentViewFragment = fragment
        supportFragmentManager.transaction {
            if (addBackStackNull == 1) {
                addToBackStack(null)
            }
            replace(R.id.fragment_container, fragment, tag)
        }
    }

    fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            fragment_container.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            fragment_container.visibility = View.VISIBLE
        }
    }

    private fun retrieveMainViewModel() = ViewModelProvider(this,Injection.provideViewModelFactory()).get(RecipeRepositoriesViewModel::class.java)


    override fun onBackPressed() {
        (currentViewFragment as? onBackPressedFragment)?.onBackPressed()
            ?: super.onBackPressed()
    }
}


