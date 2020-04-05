package com.example.jigsaw

import android.graphics.Color
import android.graphics.Paint

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */
object PaintManager {

    val paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
    }
}