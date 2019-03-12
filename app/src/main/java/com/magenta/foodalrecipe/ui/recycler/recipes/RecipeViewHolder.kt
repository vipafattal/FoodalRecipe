package com.magenta.foodalrecipe.ui.recycler.recipes

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.ui.MainActivity
import com.magenta.foodalrecipe.ui.ViewModel.DetailsViewModel
import com.magenta.foodalrecipe.ui.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.ui.fragment.BaseFragment
import com.magenta.foodalrecipe.ui.fragment.DetailsFragment
import com.magenta.foodalrecipe.ui.fragment.HomeFragment
import com.magenta.foodalrecipe.utils.commen.CACHE_IMAGES
import com.magenta.foodalrecipe.utils.commen.SharedPrefererance
import com.magenta.foodalrecipe.utils.commen.round
import com.magenta.foodalrecipe.utils.extensions.*
import kotlinx.android.synthetic.main.item_recipe.view.*
import kotlinx.android.synthetic.main.item_recipe_top.view.*
import kotlinx.android.synthetic.main.layout_rate.view.*
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

    private val preference = SharedPrefererance.instance
    private val isCachingImages = preference.getBoolean(CACHE_IMAGES, true)

    fun bindTrendingData(data: PagedList<Recipe>) {
        val views =
            listOf(
                itemView.topRecipe1
                , itemView.topRecipe2
                , itemView.topRecipe3
            )

        itemView.see_all_trending.setOnClickListener {
            val activity = itemView.context as MainActivity
            val fragment = HomeFragment.newTrendingInstance()
            activity.updateCurrentFragment(fragment, HomeFragment.TAG + 't', 1)
        }
        for (i in 0 until views.size) {

            data[i]?.let { recipe ->

                views[i].top_recipe_title.text = recipe.title

                val topRecipeImg = views[i].top_recipe_img

                topRecipeImg.glide(recipe.image, isCachingImages)
                views[i].recipeRate.text = getRate(recipe.rank).toString()

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
            recipeImg.glide(data.image, isCachingImages)
            recipeRate.text = getRate(data.rank).toString()
            onItemClick(data)
            onLongClick {

            }
            activateFavAction(data)
        }
    }


    private fun getRate(rank: Double) = (rank / 20.0).round(1)

    private fun View.onItemClick(recipe: Recipe) {
        setOnClickListener {
            val activity = itemView.context as MainActivity

            (activity.currentViewFragment as? BaseFragment)?.animateRecyclerView(this.parent as View) {
                activity.viewModelOf(DetailsViewModel::class.java).setCurrentRecipe(recipe)
                val detailsFragment = DetailsFragment()
                activity.updateCurrentFragment(detailsFragment, DetailsFragment.TAG, 1)
            }
        }
    }


}