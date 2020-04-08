package com.example.jigsaw.interfaces

import com.example.jigsaw.models.Tile
import com.example.jigsaw.widgets.TileView

/**
 * Created by Dario Bruzzese
 * on 4/9/2020
 */
interface OnTileSelectedListener {
    fun onTileSelected(view: TileView, tile: Tile)
}