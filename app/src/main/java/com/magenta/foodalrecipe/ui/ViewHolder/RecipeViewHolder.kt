package com.magenta.foodalrecipe.ui.ViewHolder

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.magenta.foodalrecipe.MainActivity
import com.magenta.foodalrecipe.ViewModel.DetailsViewModel
import com.magenta.foodalrecipe.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.ui.BaseFragment
import com.magenta.foodalrecipe.ui.DetailsFragment
import com.magenta.foodalrecipe.utils.commen.extensions.glide
import com.magenta.foodalrecipe.utils.commen.extensions.inflater
import com.magenta.foodalrecipe.utils.commen.extensions.lottieAnimationControl
import com.magenta.foodalrecipe.utils.commen.extensions.viewModelOf
import com.magenta.foodalrecipe.utils.commen.round
import kotlinx.android.synthetic.main.item_recipe.view.*
import kotlinx.android.synthetic.main.item_recipe_top.view.*
import kotlinx.android.synthetic.main.layout_top_recipe.view.*

/**
 * Created by ${User} on ${Date}
 */
class RecipeViewHolder(
    private val viewModel: RecipeRepositoriesViewModel,
    parent: ViewGroup,
    @LayoutRes layoutId: Int,
    private val withFavAction: Boolean
) : RecyclerView.ViewHolder(parent.inflater(layoutId)) {

    fun bindTrendingData(data: PagedList<Recipe>) {
        val views =
            listOf(
                itemView.topRecipe1
                , itemView.topRecipe2
                , itemView.topRecipe3
            )

        for (i in 0 until views.size) {

            data[i]?.let { recipe ->

                views[i].top_recipe_title.text = recipe.title

                val topRecipeImg = views[i].top_recipe_img

                topRecipeImg.glide(recipe.image)
                views[i].trendingRecipeRate.text = getRate(recipe.rank).toString()

                views[i].top_recipe_img.onItemClick(recipe)

                if (recipe.isBookMarked)
                    views[i].topRecipeFavAnim.progress = 1.0f
                else
                    views[i].topRecipeFavAnim.progress = 0.0f

            }
        }

    }

    private fun View.activateFavAction(
        oldRecipe: Recipe
    ) {
        var bookMarked = oldRecipe.isBookMarked
        if (bookMarked) {
            recipeFavAnim.progress = 1.0f
            unFav_img.alpha = 0f
        } else {
            recipeFavAnim.progress = 0.0f
            unFav_img.alpha = 1f
        }

        if (withFavAction) {
            recipeFavAnim.setOnClickListener {
                bookMarked = !bookMarked

                if (bookMarked)
                    unFav_img.alpha = 0f

                viewModel.updateFavState(bookMarked, oldRecipe.id)
                recipeFavAnim.lottieAnimationControl(1600, !bookMarked) {
                    if (!bookMarked)
                        unFav_img.alpha = 1f

                }
            }
        }
    }

    fun bindRecipeData(data: Recipe) {
        itemView.apply {
            recipeTitle.text = data.title
            recipeImg.glide(data.image)
            recipeRate.text = getRate(data.rank).toString()
            onItemClick(data)
            activateFavAction(data)
        }
    }


    private fun getRate(rank: Double) = (rank / 20.0).round(1)

    private fun View.onItemClick(recipe: Recipe) {
        setOnClickListener {
            val activity = itemView.context as MainActivity
            activity.viewModelOf(DetailsViewModel::class.java).setCurrentRecipe(recipe)
            val detailsFragment = DetailsFragment()
            activity.updateCurrentFragment(detailsFragment, DetailsFragment.TAG, 1)
        }
    }


}