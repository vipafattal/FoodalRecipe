package com.magenta.foodalrecipe.model

/**
 * Created by ${User} on ${Date}
 */

data class Search(val keyword:String,val searchOnline:Boolean, val sortType:Char):BaseData()