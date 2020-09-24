package com.magenta.foodalrecipe.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.framework.Injection
import com.magenta.foodalrecipe.ui.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.ui.recycler.recipes.RecipesAdapter
import com.magenta.foodalrecipe.utils.commen.onBackPressedFragment
import com.magenta.foodalrecipe.utils.extensions.animateElevation
import com.magenta.foodalrecipe.utils.extensions.observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(), onBackPressedFragment {

    override val viewRes: Int = R.layout.fragment_home
    private lateinit var viewModel: RecipeRepositoriesViewModel
    private var isShowingTrending = false
    private var cardElevation: Float = 0f

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        RecipesAdapter(
            viewModel,
            isTopTrendingItem = !isShowingTrending,
            withFavAction = true
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isShowingTrending) {
            trending_title.visibility = View.VISIBLE
            cardElevation = resources.getDimension(R.dimen.toolbar_elevation)
        }

        showNavigation()
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(RecipeRepositoriesViewModel::class.java)

        initAdapter()
    }

    private fun initAdapter() {
        homeRV.scrollRegister()
        homeRV.adapter = adapter

        viewModel.recipes.observer(this) {
            Log.d("HomeFragment", "list: ${it.size}")
            parentActivity.showEmptyList(it.size == 0)
            adapter.submitList(it)
        }

        viewModel.networkErrors.observer(this) {
            Toast.makeText(context, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        }

        viewModel.getRecipes(sortTypeChar = if (isShowingTrending) 't' else 'r')
    }


    override fun doOnScroll() {
        if (isShowingTrending) {
            val layoutManger = homeRV.layoutManager as LinearLayoutManager

            if (layoutManger.findFirstCompletelyVisibleItemPosition() == 0) {
                if (trending_title.cardElevation > 0f)
                    trending_title.animateElevation(true)
            } else {
                trending_title.cardElevation = cardElevation
            }
        }
    }

    override fun onBackPressed() {
        if (isShowingTrending)
            parentActivity.magentaNav.callOnClickNavigation(0)
        else {
            parentActivity.finish()
            System.exit(0)
        }
    }

    override fun onResume() {
        super.onResume()
        showNavigation()
    }

    companion object {
        fun newTrendingInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            homeFragment.isShowingTrending = true
            return homeFragment
        }

        const val TAG = "HomeFragment"
    }
}
