package com.example.jigsaw.models

import android.graphics.Bitmap
import com.example.jigsaw.enums.CapMode

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */

class TileFull : Tile() {
    var capLeft = CapMode.NONE
    var capTop = CapMode.NONE
    var capRight = CapMode.NONE
    var capBottom = CapMode.NONE

    var index = Index(0, 0)

    var position = 0

    var bitmap: Bitmap? = null

    var tileDecorator: TileDecorator? = null
}