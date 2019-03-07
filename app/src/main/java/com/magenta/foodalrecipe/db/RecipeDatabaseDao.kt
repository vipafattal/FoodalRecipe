package com.magenta.foodalrecipe.db

import androidx.paging.DataSource
import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.utils.commen.RecipeTableName

/**
 * Created by ${User} on ${Date}
 */

@Dao
interface RecipeDatabaseDao {

    @Query("SELECT * FROM $RecipeTableName ORDER BY rank DESC, title ASC")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM $RecipeTableName WHERE isTrending LIKE :isSortByTrend ORDER BY rank DESC, title ASC")
    fun getAllRecipe(isSortByTrend: Boolean): DataSource.Factory<Int, Recipe>

    @Query("SELECT * FROM $RecipeTableName WHERE title LIKE :titleQuery COLLATE NOCASE ORDER BY rank DESC, title ASC")
    fun getByTitleRecipe(titleQuery: String): DataSource.Factory<Int, Recipe>

    @Query("SELECT * FROM $RecipeTableName WHERE isBookMarked LIKE :isFavorited ORDER BY rank DESC, title ASC")
    fun getFavoritedList(isFavorited:Boolean):MutableList<Recipe>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRecipeList(recipes: List<Recipe>)

    @Update(onConflict = REPLACE)
    suspend fun updateRecipe(recipe: Recipe)

    @Query("Update $RecipeTableName Set isBookMarked = :favState Where id Like :ID")
    suspend fun updateFavouriteState(favState: Boolean, ID: String)
}