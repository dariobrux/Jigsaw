package com.example.jigsaw.extensions

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

/**
 * Created by Dario Bruzzese
 * on 4/11/2020
 */

fun Context.getScreenSize() : Point {
    val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}