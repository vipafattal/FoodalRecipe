package com.magenta.foodalrecipe.utils.commen

fun String.match(words: String, caseSensitive: Boolean = true): Boolean =
        if (caseSensitive)
            matches("(.*)$words(.*)".toRegex())
        else
            matches("(?i:(.*)$words(.*))".toRegex())

