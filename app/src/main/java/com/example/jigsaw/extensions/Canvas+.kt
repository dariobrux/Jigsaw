package com.example.jigsaw.extensions

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */

fun Canvas.drawIntCircle(cx: Int, cy: Int, radius: Float, paint: Paint) {
    this.drawCircle(cx.toFloat(), cy.toFloat(), radius, paint)
}