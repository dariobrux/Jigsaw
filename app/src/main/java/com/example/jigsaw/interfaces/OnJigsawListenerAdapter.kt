package com.example.jigsaw.interfaces

import android.view.View
import com.example.jigsaw.widgets.TileView

/**
 * Created by Dario Bruzzese
 * on 4/9/2020
 */
abstract class OnJigsawListenerAdapter : OnJigsawListener {

    override fun onTileSelected(view: View) {
        // Not implemented
    }

    override fun onTileDeselected(view: View) {
        // Not implemented
    }

    override fun onTileSettled(view: View) {
        // Not implemented
    }
}