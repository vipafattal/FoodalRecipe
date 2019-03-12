package com.magenta.foodalrecipe.framework.data.PagedData

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.magenta.foodalrecipe.framework.data.Food2ForkLocalCache
import com.magenta.foodalrecipe.framework.data.api.Food2ForkAPI
import com.magenta.foodalrecipe.model.Recipe


/**
 * Created by ${User} on ${Date}
 */

class RecipePagedDataSourceBuilder(
    private val api: Food2ForkAPI,
    private val cache: Food2ForkLocalCache,
    private val searchQuery: String,
    private val sortType: Char
) : DataSource.Factory<Int, Recipe>() {


    var networkErrors: MutableLiveData<String> = MutableLiveData()

    override fun create(): DataSource<Int, Recipe> {
        val pagedDataSource = RecipePagedDataSource(api,cache, searchQuery, sortType,networkErrors)
        return pagedDataSource
    }

}