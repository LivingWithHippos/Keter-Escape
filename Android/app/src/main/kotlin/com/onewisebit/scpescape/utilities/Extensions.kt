package com.onewisebit.scpescape.utilities

import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    context?.let{
        val inputMethodManager = it.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
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