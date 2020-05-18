package scp.libraries.ui.utilities

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

fun GradientDrawable.setIntColor(value: Int) {
    this.setColor(
        Color.argb(
            255,
            Color.red(value),
            Color.green(value),
            Color.blue(value)
        )
    )
}