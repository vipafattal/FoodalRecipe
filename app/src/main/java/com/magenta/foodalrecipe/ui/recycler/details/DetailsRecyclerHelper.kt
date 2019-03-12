package com.magenta.foodalrecipe.ui.recycler.details

import com.magenta.foodalrecipe.model.Ingredient

/**
 * Created by ${User} on ${Date}
 */
object DetailsRecyclerHelper {

    fun formatIngredientItem(data: String): Ingredient {
        // val spliced = data.substring(0,Math.min(data.length,12)).split('(', ')', limit = 3)
        var numberSting = ""
        var unit = ""
        val detail: String


        var newData = data
        var modificationNotHappened = 0
        var isInsideBracket = false

        loop@ for (index in 0 until data.length) {
            val c = data[index]
            when {
                c.isDigit() && !isInsideBracket -> {
                    modificationNotHappened = 0
                    numberSting += c
                    newData = newData.replaceRange(index, index + 1, " ")
                }

                c == '/' -> {
                    modificationNotHappened = 0
                    numberSting += c
                    newData = newData.replaceRange(index, index + 1, " ")
                }

                c == '(' -> isInsideBracket = true

                c == ')' -> break@loop

                else -> if (!isInsideBracket) modificationNotHappened++
            }

            if (modificationNotHappened == 4)
                break
        }

        detail = newData
        if (numberSting == "")
            numberSting = "-"

        return Ingredient(numberSting.trim(), unit.trim(), detail.trimStart().capitalize())
    }
}