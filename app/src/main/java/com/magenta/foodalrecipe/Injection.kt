package com.magenta.foodalrecipe

import androidx.lifecycle.ViewModelProvider
import com.magenta.foodalrecipe.ViewModel.ViewModelFactory
import com.magenta.foodalrecipe.data.Food2ForkRepository
import com.magenta.foodalrecipe.data.api.Food2ForkAPI
import com.magenta.foodalrecipe.db.Food2ForkLocalCache
import com.magenta.foodalrecipe.db.RecipeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Created by ${User} on ${Date}
 */
object Injection {
    private val coroutineMainScope = CoroutineScope(Dispatchers.IO)
    private fun provideCache(): Food2ForkLocalCache {
        return Food2ForkLocalCache(RecipeDatabase.getRecipeDao(), coroutineMainScope)
    }

    private fun provideGithubRepository(): Food2ForkRepository {
        return Food2ForkRepository(Food2ForkAPI.create(), provideCache())
    }
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository())
    }
}