package com.magenta.foodalrecipe.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.magenta.foodalrecipe.data.Food2ForkRepository

/**
 * Factory for ViewModels creation
 */

class ViewModelFactory(private val repository: Food2ForkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeRepositoriesViewModel::class.java)) {
            repository
            @Suppress("UNCHECKED_CAST")
            return RecipeRepositoriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}