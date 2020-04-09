package com.example.jigsaw.interfaces

import android.view.View
import com.example.jigsaw.widgets.TileView

/**
 * Created by Dario Bruzzese
 * on 4/9/2020
 */
interface OnJigsawListener {

    fun onTileSelected(view: View)

    fun onTileDeselected(view: View)

    fun onTileSettled(view: View)
}