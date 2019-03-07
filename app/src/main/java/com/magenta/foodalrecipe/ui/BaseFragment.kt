package com.magenta.foodalrecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.magenta.foodalrecipe.MainActivity
import com.magenta.foodalrecipe.utils.commen.extensions.onScroll
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by ${User} on ${Date}
 */

abstract class BaseFragment : Fragment() {

    abstract val viewRes: Int
    protected lateinit var parentActivity: MainActivity
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentActivity = activity as MainActivity
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(viewRes, container, false)
    }

    fun RecyclerView.scrollRegister() {
        onScroll { dx, dy ->
            if (dy > 0) navigationTranslation(outOfScreen = true)
            if (dy < 0) navigationTranslation(outOfScreen = false)
        }
    }


    private fun navigationTranslation(outOfScreen: Boolean) {
        if (outOfScreen)
            hideNavigation()
        else
            showNavigation()
    }

    fun hideNavigation(immediate:Boolean=false) {
        if(!immediate)
        parentActivity.magentaNav
            .animate()
            .setDuration(250)
            .translationY(parentActivity.magentaNav.height.toFloat())
            .start()
        else
            parentActivity.magentaNav.translationY = -parentActivity.magentaNav.height.toFloat()
    }

    fun showNavigation() {
        parentActivity.magentaNav
            .animate()
            .setDuration(400)
            .translationY(0f)
            .start()
    }

}