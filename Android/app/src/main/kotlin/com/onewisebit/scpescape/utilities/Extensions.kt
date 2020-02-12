package com.onewisebit.scpescape.utilities

import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    val inputMethodManager =
        context!!.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun <T> MutableList<T>.pop() : T? {
    val item = lastOrNull()

    if (!isEmpty()){
        removeAt(size -1)
    }
    return item
}