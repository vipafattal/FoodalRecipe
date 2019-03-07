package com.magenta.foodalrecipe.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.magenta.foodalrecipe.utils.commen.RecipeTableName

/**
 * Created by ${User} on ${Date}
 */

@Suppress("ArrayInDataClass")
@Entity(tableName = RecipeTableName)
data class Recipe(
    @SerializedName("recipe_id")
    @PrimaryKey
    val id: String,
    val title: String,
    @SerializedName("social_rank")
    val rank: Double,
    @SerializedName("image_url")
    val image: String,
    val source_url: String,
    val publisher_url: String,
    val publisher: String,
    val ingredients: List<String>? = null,
    val isBookMarked: Boolean = false,
    var isTrending:Boolean = false
) :BaseData()