package com.magenta.foodalrecipe.framework.db

import androidx.room.TypeConverter
import com.google.gson.Gson


/**
 * Created by ${User} on ${Date}
 */
class ArrayConverter {

    @TypeConverter
    fun listToJson(value: List<String>?): String =
        Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<String>? =
        if (value != "null" )
            Gson().fromJson(value, Array<String>::class.java).toList()
        else
            null



}