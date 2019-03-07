package com.magenta.foodalrecipe.model

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList

/**
 * Created by ${User} on ${Date}
 */
data class Food2ForkResponse(
    val data: LiveData<PagedList<Recipe>>,
    val networkErrors:LiveData<String>
)
