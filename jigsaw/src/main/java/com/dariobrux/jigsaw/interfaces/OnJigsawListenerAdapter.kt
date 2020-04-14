package com.dariobrux.jigsaw.interfaces

import android.view.View

/**
 * Created by Dario Bruzzese
 * on 4/9/2020
 */
abstract class OnJigsawListenerAdapter : OnJigsawListener {

    override fun onTileDeselected(view: View, isInBoard: Boolean) {
        // Not implemented
    }

    override fun onTileGenerated(view: View) {
        // Not implemented
    }

    override fun onTileSelected(view: View) {
        // Not implemented
    }

    override fun onTileSettled(view: View, isCorrectPosition: Boolean) {
        // Not implemented
    }

    override fun onTileUnsettled(view: View) {
        // Not implemented
    }
}