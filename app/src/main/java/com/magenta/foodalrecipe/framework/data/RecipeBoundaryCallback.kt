package com.magenta.foodalrecipe.framework.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.magenta.foodalrecipe.framework.data.api.Food2ForkAPI
import com.magenta.foodalrecipe.framework.data.api.foodRepos
import com.magenta.foodalrecipe.model.Recipe

/**
 * This boundary callback gets notified when user reaches to the edges of the list for example when
 * the database cannot provide any more data.
 **/
class RecipeBoundaryCallback(
    private val query: String,
    private val sortType: Char,
    private val service: Food2ForkAPI,
    private val cache: Food2ForkLocalCache) : PagedList.BoundaryCallback<Recipe>() {

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     *when the app is started for the first time and the database is empty,
     *it calls BoundaryCallback.onZeroItemsLoaded()
     */
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Recipe) {
        super.onItemAtEndLoaded(itemAtEnd)
        requestAndSaveData()
    }


    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        //insert data from server to the database
        service.foodRepos( query, sortType, lastRequestedPage++ //RecipesAdapter.CurrentPage
            , { recipes ->
                cache.insert(recipes) { isNextPage ->
                    isRequestInProgress = false
                    if (isNextPage)
                        requestAndSaveData()
                }
            }, { error ->
                _networkErrors.postValue(error)
                isRequestInProgress = false

            })

    }
}