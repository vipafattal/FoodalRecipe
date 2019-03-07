package com.magenta.foodalrecipe.data.PagedData

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.magenta.foodalrecipe.data.api.Food2ForkAPI
import com.magenta.foodalrecipe.data.api.foodRepos
import com.magenta.foodalrecipe.db.Food2ForkLocalCache
import com.magenta.foodalrecipe.model.Recipe

/**
 * Created by ${User} on ${Date}
 */

class RecipePagedDataSource(
    private val api: Food2ForkAPI,
    private val cache: Food2ForkLocalCache,
    private val searchQuery: String,
    private val sortType: Char,
    private val networkErrors: MutableLiveData<String>
) : PageKeyedDataSource<Int, Recipe>() {

    private val TAG = RecipePagedDataSource::class.java.simpleName


    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Recipe>) {
        if (!isRequestInProgress) {
            isRequestInProgress = true
            api.foodRepos(searchQuery, sortType, 1, onSuccess = {
                callback.onResult(it, null, 2)
                cache.insert(it){
                    isRequestInProgress = false
                }
            }, onError = {
                isRequestInProgress = false
                networkErrors.postValue(it)
            })
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Recipe>) {
        if (!isRequestInProgress) {
            isRequestInProgress = true
            Log.i(TAG, "loadAfter: page " + params.key + " Count " + params.requestedLoadSize)
            api.foodRepos(searchQuery, sortType, params.key, {
                isRequestInProgress = false
                val adjacentKey = if (it.isEmpty()) null else params.key + 1
                callback.onResult(it, adjacentKey)
                cache.insert(it){
                    isRequestInProgress = false
                }
            }, {
                isRequestInProgress = false
                networkErrors.postValue(it)
            })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Recipe>) {
    }
}