package com.hackerearth.searchbar.utils

object GenricUtils {
    fun isListEmpty(toCheck: List<*>?): Boolean {
        return toCheck == null || toCheck.isEmpty() || toCheck.size == 1 && toCheck[0] == null
    }

}