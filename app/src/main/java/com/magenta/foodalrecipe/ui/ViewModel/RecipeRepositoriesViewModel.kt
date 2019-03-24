package com.magenta.foodalrecipe.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.magenta.foodalrecipe.framework.data.Food2ForkRepository
import com.magenta.foodalrecipe.model.Food2ForkResponse
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.model.Search

/**
 * This's the main ViewModel for screens.
 * The ViewModel works with the [Food2ForkRepository] to get the data.
 */
class RecipeRepositoriesViewModel(private val repository: Food2ForkRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<Search>()

    private val food2ForkResult: LiveData<Food2ForkResponse> = Transformations.map(queryLiveData) {
        if (it.searchOnline)
            repository.food2ForkPagedResponse(it.keyword, it.sortType)
        else
            repository.food2ForkResponse(it.keyword,it.sortType)
    }
    private val food2ForkTrendingResult: LiveData<Food2ForkResponse> = Transformations.map(queryLiveData) {
        repository.food2ForkResponse(it.keyword, 't')
    }

    val trendingRecipes: LiveData<PagedList<Recipe>> = Transformations.switchMap(food2ForkTrendingResult) {
        it.data
    }

    val recipes: LiveData<PagedList<Recipe>> = Transformations.switchMap(food2ForkResult) {
        it.data
    }

    val networkErrors: LiveData<String> = Transformations.switchMap(food2ForkResult) {
        it.networkErrors
    }

    fun updateRecipe(newRecipe: Recipe) {

        repository.updateRecipe(newRecipe)
    }

    fun updateFavState(favouriteSate: Boolean, ID: String) {
        repository.updateFavState(favouriteSate, ID)
    }

    fun getRecipes(searchQuery: String = "", searchOnline: Boolean = false, sortTypeChar: Char = 'r') {
        queryLiveData.postValue(Search(searchQuery, searchOnline, sortTypeChar))
    }

    fun deleteByFavoriteState(favouriteSate: Boolean) {
        repository.deleteByFavoriteState(favouriteSate)
    }

    fun getFavoritedList(isFavorited:Boolean):MutableList<Recipe> =repository.getFavoritedList(isFavorited)


    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value?.keyword

}