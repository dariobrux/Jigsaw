package com.example.jigsaw.extensions

import android.view.View

/**
 * Created by Dario Bruzzese
 * on 4/11/2020
 */

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}