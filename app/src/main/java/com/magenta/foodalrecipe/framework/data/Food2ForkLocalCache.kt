package com.magenta.foodalrecipe.framework.data

import androidx.paging.DataSource
import com.magenta.foodalrecipe.framework.db.RecipeDatabaseDao
import com.magenta.foodalrecipe.model.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by ${User} on ${Date}
 */
class Food2ForkLocalCache(
    private val repoDao: RecipeDatabaseDao,
    private val coroutineScope: CoroutineScope
) {

    fun insert(recipes: List<Recipe>, insertFinished: (nextPage: Boolean) -> Unit) {
        coroutineScope.launch {
            var isNextPage = true

            val filteredData = recipes.filterRecipeData()


            if (filteredData.isNotEmpty()) {
                repoDao.insertRecipeList(filteredData)
                isNextPage = false
            }

            insertFinished(isNextPage)


        }
    }

    private fun List<Recipe>.filterRecipeData(): List<Recipe> {
        val allSavedData = repoDao.getAll()

        if (allSavedData.isNotEmpty()) {
            val filteredData = mutableListOf<Recipe>()

            for (index in 0 until size) {
                val recipe = allSavedData.firstOrNull {
                    it.id == this[index].id
                }

                if (recipe == null)
                    filteredData.add(this[index])
            }
            return filteredData
        }
        return this
    }

    fun updateRecipe(newRecipe: Recipe) {
        coroutineScope.launch {
            repoDao.updateRecipe(newRecipe)
        }
    }

    fun updateFavState(favouriteSate: Boolean, ID: String) {
        coroutineScope.launch {
            repoDao.updateFavouriteState(favouriteSate, ID)
        }
    }
    fun deleteByFavoriteState(favouriteSate: Boolean) {
        coroutineScope.launch {
            repoDao.deleteByFavoriteState(favouriteSate)
        }
    }

    fun getRecipes(title: String, sortType: Char): DataSource.Factory<Int, Recipe> {
        val isTrending = sortType == 't'

        return if (title.isNotEmpty() && title.isNotBlank()) {
            repoDao.getByTitleRecipe("%$title%")
        } else
            repoDao.getAllRecipe(isTrending)
    }

    fun getFavourtedList(isFavorited:Boolean) = repoDao.getFavoritedList(isFavorited)


}
