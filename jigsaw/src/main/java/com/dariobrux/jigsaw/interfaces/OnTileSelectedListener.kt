package com.dariobrux.jigsaw.interfaces

import android.view.View
import com.dariobrux.jigsaw.adapters.GridAdapter
import com.dariobrux.jigsaw.models.TileFull
import com.dariobrux.jigsaw.widgets.TileView

/**
 * Created by Dario Bruzzese
 * on 4/9/2020
 */
interface OnTileSelectedListener {
    fun onTileSelected(adapter: GridAdapter, view: TileView, tile: TileFull, position: Int)
    fun onEmptySelected(adapter: GridAdapter, view: View, position: Int)
}