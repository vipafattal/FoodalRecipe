package com.magenta.foodalrecipe.ui.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magenta.foodalrecipe.model.Recipe

/**
 * Created by ${User} on ${Date}
 */
class DetailsViewModel : ViewModel() {

     private lateinit var currentRecipe: MutableLiveData<Recipe>

    fun setCurrentRecipe(recipe: Recipe) {
        if (!::currentRecipe.isInitialized)
            currentRecipe= MutableLiveData()
        currentRecipe.value = recipe
    }

    fun getCurrentRecipe()= currentRecipe.value
}
