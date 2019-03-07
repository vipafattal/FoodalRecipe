package com.magenta.foodalrecipe

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.ui.BaseFragment
import com.magenta.foodalrecipe.ui.FavoriteFragment
import com.magenta.foodalrecipe.ui.HomeFragment
import com.magenta.foodalrecipe.ui.SearchFragment
import com.magenta.foodalrecipe.utils.commen.extensions.transaction
import com.magenta.foodalrecipe.utils.customViews.onClick.OnNavItemClicked
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnNavItemClicked {

    private var currentViewFragment: BaseFragment = HomeFragment()
    private var currentPosition = -1

    override fun onMagentaNavItemClicked(id: Int, position: Int) {
        if (currentPosition != position) {
            currentPosition = position
            when (position) {
                0 -> updateCurrentFragment(HomeFragment(), HomeFragment.TAG)
                1 -> updateCurrentFragment(SearchFragment(), SearchFragment.TAG, 1)
                2 -> updateCurrentFragment(FavoriteFragment(), FavoriteFragment.TAG)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.findFragmentByTag(currentViewFragment.tag)
            ?: magentaNav.callOnClickNavigation(0)
    }


    fun updateCurrentFragment(fragment: BaseFragment, tag: String, addBackStackNull: Int = 0) {
        currentViewFragment = fragment
        supportFragmentManager.transaction {
            if (addBackStackNull == 1)
                addToBackStack(null)
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            magentaNav.animateNavigationItem(0)
            currentPosition = 0
        }
        super.onBackPressed()
    }
}


