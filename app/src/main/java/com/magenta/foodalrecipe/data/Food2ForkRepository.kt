package com.magenta.foodalrecipe.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.magenta.foodalrecipe.data.PagedData.RecipePagedDataSourceBuilder
import com.magenta.foodalrecipe.data.api.Food2ForkAPI
import com.magenta.foodalrecipe.db.Food2ForkLocalCache
import com.magenta.foodalrecipe.model.Food2ForkResponse
import com.magenta.foodalrecipe.model.Recipe


/**
 * Repository class that works with local and remote data sources.
 */
class Food2ForkRepository(
    private val service: Food2ForkAPI,
    private val cache: Food2ForkLocalCache
) {
    private val pageSize= 15
    //Invoked once every new query
    fun food2ForkResponse(query: String, sortType: Char = 'r'): Food2ForkResponse {


        // Get data from the local cache
        val dataSourceFactory = cache.getRecipes(query, sortType)

        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        val boundaryCallback = RecipeBoundaryCallback(query, sortType, service, cache)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, pageSize)
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return Food2ForkResponse(data, networkErrors)
    }

    fun food2ForkPagedResponse(query: String, sortType: Char = 'r'): Food2ForkResponse {
        val dataSourceFactory = RecipePagedDataSourceBuilder(service,cache, query, sortType)
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(pageSize)
            .build()

        val data: LiveData<PagedList<Recipe>> = LivePagedListBuilder(dataSourceFactory, config).build()

        return Food2ForkResponse(data,dataSourceFactory.networkErrors)
    }


    fun updateRecipe(newRecipe: Recipe) {
        cache.updateRecipe(newRecipe)
    }

    fun updateFavState(favouriteSate: Boolean, ID: String) {
        cache.updateFavState(favouriteSate, ID)
    }


    fun getFavoritedList(isFavorited:Boolean) :MutableList<Recipe> = cache.getFavourtedList(isFavorited)


}