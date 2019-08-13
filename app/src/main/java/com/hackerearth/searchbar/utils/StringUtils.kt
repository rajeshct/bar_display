package com.hackerearth.searchbar.utils

object StringUtils {
    fun equals(original: String?, checking: String?): Boolean {
        return if (original != null && checking != null) {
            original == checking
        } else false
    }

    fun equalsIgnoreCase(original: String?, toCheck: String?): Boolean {
        return if (toCheck == null || original == null) {
            false
        } else original.equals(toCheck, ignoreCase = true)
    }
}