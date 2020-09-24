package com.magenta.foodalrecipe.ui.fragment

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.ui.MainActivity
import com.magenta.foodalrecipe.utils.commen.unitFun
import com.magenta.foodalrecipe.utils.extensions.onScroll
import com.magenta.navigation.helpers.doOnEnd
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
        onScroll { _, dy ->
            doOnScroll()
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

    fun hideNavigation(immediate: Boolean = false) {
        if (!immediate)
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

    fun animateRecyclerView(rv:View?,onFinished:unitFun) {
        AnimatorInflater.loadAnimator(parentActivity, R.animator.recipe_list_animator).apply {
            setTarget(rv)
            doOnEnd {
                onFinished()
            }
            start()
        }
    }

    open fun doOnScroll() {}

}