package com.magenta.foodalrecipe.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.framework.Injection
import com.magenta.foodalrecipe.framework.data.api.ingredientRepos
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.ui.ViewModel.DetailsViewModel
import com.magenta.foodalrecipe.ui.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.ui.recycler.details.DetailsRecyclerAdapter
import com.magenta.foodalrecipe.utils.commen.round
import com.magenta.foodalrecipe.utils.extensions.errorNotifier
import com.magenta.foodalrecipe.utils.extensions.glide
import com.magenta.foodalrecipe.utils.extensions.lottieAnimationControl
import com.magenta.foodalrecipe.utils.extensions.viewModelOf
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailsFragment : BaseFragment() {

    private lateinit var recipe: Recipe
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var recipeRepoViewModel: RecipeRepositoriesViewModel
    private lateinit var detailsViewModel: DetailsViewModel
    override val viewRes: Int = R.layout.fragment_details

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideNavigation()
        initViewModel()

        recipe = detailsViewModel.getCurrentRecipe()!!

        dispatchDataToView()

        coroutineScope.launch {
            if (recipe.ingredients == null) {
                progress_ingredients.visibility = View.VISIBLE
                Log.v("Ingredients Available", "False")
                getIngredients()
                detailsViewModel.setCurrentRecipe(recipe)
                dispatchDataToRecycler()
            } else {
                Log.d("Ingredients Available", "True")
                dispatchDataToRecycler()
            }
        }
    }

    private fun dispatchDataToView() {
        details_img.glide(recipe.image, true)
        details_title.text = recipe.title
        details_rating_bar.rating = getRate(recipe.rank).toFloat()
        publisher_link_text.text = recipe.source_url
        activateFavAction()
    }

    private fun dispatchDataToRecycler() {
        progress_ingredients.visibility = View.GONE
        recipe.ingredients?.let { recycler_ingredients.adapter = DetailsRecyclerAdapter(it) }
    }

    private fun initViewModel() {
        detailsViewModel = viewModelOf(DetailsViewModel::class.java)
        recipeRepoViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(RecipeRepositoriesViewModel::class.java)
    }

    private suspend fun getIngredients() {
        ingredientRepos(recipe.id) { ingredients, errorMessage ->
            if (errorMessage.isNullOrEmpty()) {
                recipe = recipe.copy(ingredients = ingredients)
                recipeRepoViewModel.updateRecipe(recipe)
            } else {
                context?.errorNotifier("Connection Error", errorMessage)
                delay(400)
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun goToUrl(url: String) {
        val uriUrl = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }

    private fun activateFavAction() {
        var bookMarked = recipe.isBookMarked
        if (bookMarked) {
            recipeFavAnim_details.progress = 1.0f
            unFav_img_details.alpha = 0f
        } else {
            recipeFavAnim_details.progress = 0.0f
            unFav_img_details.alpha = 1f
        }

        recipeFavAnim_details.setOnClickListener {
            bookMarked = !bookMarked
            if (bookMarked)
                unFav_img_details.alpha = 0f

            recipeRepoViewModel.updateFavState(bookMarked, recipe.id)
            recipeFavAnim_details.lottieAnimationControl(1600, !bookMarked) {
                if (!bookMarked)
                    unFav_img_details.alpha = 1f

            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        job.cancelChildren()
    }

    private fun getRate(rank: Double) = (rank / 20.0).round(1)

    companion object {
        const val TAG = "DetailsFragment"
    }

}
