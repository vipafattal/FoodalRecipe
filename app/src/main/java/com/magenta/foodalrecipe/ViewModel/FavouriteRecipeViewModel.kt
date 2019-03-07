package com.magenta.foodalrecipe.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magenta.foodalrecipe.model.Recipe

/**
 * Created by ${User} on ${Date}
 */
class FavouriteRecipeViewModel : ViewModel() {

    private lateinit var favouriteRecipes: MutableLiveData<MutableList<Recipe>>

    fun addModifiedFavourite(recipe: Recipe) {
        if (!::favouriteRecipes.isInitialized)
            favouriteRecipes = MutableLiveData()

        val mutableList = favouriteRecipes.value!!

        if (mutableList.contains(recipe))
            mutableList.remove(recipe)
        else
            mutableList.add(recipe)
    }


    override fun onCleared() {
        super.onCleared()
       favouriteRecipes.value?.clear()
    }
}