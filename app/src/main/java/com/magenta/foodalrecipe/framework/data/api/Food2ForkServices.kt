package com.magenta.foodalrecipe.framework.data.api

import android.util.Log
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.utils.commen.Food2Fork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by ${User} on ${Date}
 */


inline fun Food2ForkAPI.foodRepos(
    query: String,
    sortType: Char,
    page: Int,
    crossinline onSuccess: (recipe: List<Recipe>) -> Unit,
    crossinline onError: (error: String) -> Unit
) {
    val tag = "Food2ForkService"
    getRecipes(Food2Fork, query, sortType, page).enqueue(
        object : Callback<Food2ForkAPI.API> {

            override fun onFailure(call: Call<Food2ForkAPI.API>, t: Throwable) {
                Log.d(tag, "fail to get data")
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(call: Call<Food2ForkAPI.API>, response: Response<Food2ForkAPI.API>) {
                Log.d(tag, "got a response $response")
                if (response.isSuccessful) {
                    val recipes = response.body()?.recipes ?: emptyList()

                    if (sortType == 'r')
                        onSuccess(recipes)
                    else {
                        val trendingRecipes = recipes.toMutableList()

                        for (index in 0 until trendingRecipes.size)
                            trendingRecipes[index].isTrending = true

                        onSuccess(trendingRecipes)
                    }
                } else {
                    response.raw().close()
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        })
}

suspend inline fun ingredientRepos(
    recipeId: String,
    crossinline onCallEnd: suspend (ingredient: List<String>, errorMessage: String?) -> Unit
) {
    var ingredient: List<String>? = listOf()
    var errorMessage: String? = null

    withContext(Dispatchers.IO) {
        try {
            val food2ForkAPI = Food2ForkAPI.create()
            val response = food2ForkAPI.getIngredients(rId = recipeId).await()

            if (response.isSuccessful) {
                ingredient = response.body()?.recipe?.ingredients
                if (ingredient == null)
                    errorMessage = "Rich Limit"
            } else
                errorMessage = response.errorBody()?.toString() ?: "Unknown error"
        } catch (hostException: UnknownHostException) {
            errorMessage = hostException.message
        } catch (time: SocketTimeoutException) {
            errorMessage = time.message
        }
    }


    onCallEnd(ingredient ?: emptyList(), errorMessage)
}




