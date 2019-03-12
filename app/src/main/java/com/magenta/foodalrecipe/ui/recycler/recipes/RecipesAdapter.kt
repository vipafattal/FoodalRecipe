package com.magenta.foodalrecipe.ui.recycler.recipes

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.ui.MainActivity
import com.magenta.foodalrecipe.ui.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.utils.extensions.observer

/**
 * Created by ${User} on ${Date}
 */
class RecipesAdapter(
    private val viewModel: RecipeRepositoriesViewModel,
    private val isTopTrendingItem: Boolean = false,
    private val withFavAction: Boolean=true
) : PagedListAdapter<Recipe, RecipeViewHolder>(RECIPE_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(viewModel, parent, viewType, withFavAction)
    }


    override fun getItemViewType(position: Int): Int {
        return if (position >= 1 || !isTopTrendingItem)
            R.layout.item_recipe
        else
            R.layout.layout_top_recipe
    }


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val rootView = holder.itemView
        val mContext = rootView.context as MainActivity

        if (!isTopTrendingItem){
            val data = getItem(position)
            data?.let { holder.bindRecipeData(it) }
        }
        else if (position != 0) {
            val data = getItem(position - 1)
            data?.let { holder.bindRecipeData(it) }
        } else {
            viewModel.trendingRecipes.observer(mContext) {
                if (it.size >= 3) {
                    holder.bindTrendingData(it)
                }
            }

        }
    }

    /* override fun submitList(pagedList: PagedList<Recipe>?) {
         super.submitList(pagedList)
         CurrentPage = currentList?.size ?: 1
     }*/


    companion object {
        private val RECIPE_DIFF = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem == newItem
        }
    }
}