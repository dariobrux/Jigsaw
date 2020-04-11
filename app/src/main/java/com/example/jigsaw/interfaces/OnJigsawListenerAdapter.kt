package com.example.jigsaw.interfaces

import android.view.View
import android.view.ViewParent

/**
 * Created by Dario Bruzzese
 * on 4/9/2020
 */
abstract class OnJigsawListenerAdapter : OnJigsawListener {

    override fun onTileDeselected(view: View, isInBoard: Boolean) {
        // Not implemented
    }

    override fun onTilePositioned(view: View) {
        // Not implemented
    }

    override fun onTileRemoved(view: View) {
        // Not implemented
    }

    override fun onTileSelected(view: View) {
        // Not implemented
    }

    override fun onTileSettled(view: View) {
        // Not implemented
    }
}