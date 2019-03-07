package com.magenta.foodalrecipe.data.api

import com.magenta.foodalrecipe.model.Ingredient
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.utils.commen.BaseUrl
import com.magenta.foodalrecipe.utils.commen.Food2Fork
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface Food2ForkAPI {

    data class API(
        val count: Int,
        val recipes: List<Recipe>
    )

    //https://www.food2fork.com/api/search?key=5837d0a68943d79d0c1b0eb72b5923ff&q=&page=4444
    @GET("search")
    fun getRecipes(
        @Query("key") key: String,
        @Query("q") query: String,
        @Query("sort") sortType: Char,
        @Query("page") page: Int = 1
    ): Call<API>

    @GET("get")
    fun getIngredients(
        @Query("key") key: String = Food2Fork,
        @Query("rId") rId: String
    ): Call<Ingredient>


    companion object {
        private val food2ForkAPI: Retrofit by lazy {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .addInterceptor(logger)
                .build()

            Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        }

        fun create(): Food2ForkAPI = food2ForkAPI.create(
            Food2ForkAPI::class.java
        )
    }
}