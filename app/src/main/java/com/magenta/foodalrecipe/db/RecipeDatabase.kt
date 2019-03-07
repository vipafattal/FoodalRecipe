package com.magenta.foodalrecipe.db

import androidx.room.*
import com.magenta.foodalrecipe.model.Recipe
import com.magenta.foodalrecipe.utils.AppContext.Companion.appMainContext
import com.magenta.foodalrecipe.utils.commen.DbName


/**
 * Created by ${User} on ${Date}
 */

@TypeConverters(ArrayConverter::class)
@Database(entities = [Recipe::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun getDao(): RecipeDatabaseDao

    companion object {
        private val dbInstance: RecipeDatabase by lazy {
            Room.databaseBuilder(
                appMainContext,
                RecipeDatabase::class.java,
                DbName
            ).build()
        }

         fun getRecipeDao() =
                dbInstance.getDao()


    }


}