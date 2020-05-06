package com.onewisebit.scpescape.utilities

import android.content.res.AssetManager
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    context?.let {
        val inputMethodManager =
            it.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

/**
 * Remove and returns the last element of a MutableList.
 * Useful for a stack-like behaviour.
 */
fun <T> MutableList<T>.pop(): T? {
    val item = lastOrNull()

    if (!isEmpty()) {
        removeAt(size - 1)
    }
    return item
}

fun String.addNewlineOnDots(): String {
    return replace(".", ".\n")
}

fun MutableList<String>.containsIgnoreCase(element: String): Boolean {
    this.forEach {
        if (it.equals(element, ignoreCase = true))
            return true
    }
    return false
}

/**
 * This function returns the list of files and folder found in a path of the assets folder,
 * it removes the "/" mat the end and checks again if no files are found.
 */
fun AssetManager.smartList(path: String) : Array<String>?  {
    val result = this.list(path)
    if (result.isNullOrEmpty()) {
        if (path.endsWith("/"))
            return this.list(path.dropLast(1))
    }
    return result
}