package com.magenta.foodalrecipe.framework

import androidx.lifecycle.ViewModelProvider
import com.magenta.foodalrecipe.framework.data.Food2ForkLocalCache
import com.magenta.foodalrecipe.framework.data.Food2ForkRepository
import com.magenta.foodalrecipe.framework.data.api.Food2ForkAPI
import com.magenta.foodalrecipe.framework.db.RecipeDatabase
import com.magenta.foodalrecipe.ui.ViewModel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Created by ${User} on ${Date}
 */
object Injection {
    private val coroutineMainScope = CoroutineScope(Dispatchers.IO)
    private fun provideCache(): Food2ForkLocalCache {
        return Food2ForkLocalCache(RecipeDatabase.getRecipeDao(),
            coroutineMainScope
        )
    }

    private fun provideGithubRepository(): Food2ForkRepository {
        return Food2ForkRepository(Food2ForkAPI.create(), provideCache())
    }
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository())
    }
}