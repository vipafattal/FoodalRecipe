package com.magenta.foodalrecipe.ui


import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.magenta.foodalrecipe.Injection
import com.magenta.foodalrecipe.R
import com.magenta.foodalrecipe.ViewModel.DetailsViewModel
import com.magenta.foodalrecipe.ViewModel.RecipeRepositoriesViewModel
import com.magenta.foodalrecipe.data.api.ingredientRepos
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.utils.commen.extensions.errorNotifier
import com.magenta.foodalrecipe.utils.commen.extensions.viewModelOf
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

        coroutineScope.launch {
            if (recipe.ingredients == null) {
                Log.v("Ingredients Available", "False")
                getIngredients()
                detailsViewModel.setCurrentRecipe(recipe)
            } else
                Log.v("Ingredients Available", "True")
        }
    }

    private fun initViewModel() {
        detailsViewModel = viewModelOf(DetailsViewModel::class.java)

        recipeRepoViewModel = ViewModelProviders.of(
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

    override fun onDetach() {
        super.onDetach()
        job.cancelChildren()
    }

    companion object {
        const val TAG = "DetailsFragment"

    }

}
