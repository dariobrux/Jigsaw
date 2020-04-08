package com.example.jigsaw.interfaces

import android.view.View
import com.example.jigsaw.models.TileFull
import com.example.jigsaw.widgets.TileView

/**
 * Created by Dario Bruzzese
 * on 4/9/2020
 */
interface OnTileSelectedListener {
    fun onTileSelected(view: TileView, tile: TileFull)
    fun onEmptySelected(view: View, position: Int)
}