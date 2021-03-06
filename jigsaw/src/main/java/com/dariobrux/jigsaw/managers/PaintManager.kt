package com.dariobrux.jigsaw.managers

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */
object PaintManager {

    val bitmap = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        color = Color.BLUE
    }
}