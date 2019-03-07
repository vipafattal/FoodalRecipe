package com.magenta.foodalrecipe.ui


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.magenta.foodalrecipe.Injection
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.RecipesAdapter
import com.magenta.foodalrecipe.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.utils.commen.extensions.observer
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {

    override val viewRes: Int = R.layout.fragment_home

    private lateinit var viewModel: RecipeRepositoriesViewModel
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        RecipesAdapter(
            viewModel,
            isTopTrendingItem = true,
            withFavAction = true
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showNavigation()
        viewModel = ViewModelProviders.of(
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

        viewModel.getRecipes()
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}
