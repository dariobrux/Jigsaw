package com.example.jigsaw.interfaces

import android.view.View
import android.view.ViewParent

/**
 * Created by Dario Bruzzese
 * on 4/9/2020
 */
interface OnJigsawListener {

    fun onCompleted()

    fun onTileDeselected(view: View, isInBoard: Boolean)

    fun onTilePositioned(view: View)

    fun onTileRemoved(view: View)

    fun onTileSelected(view: View)

    fun onTileSettled(view: View)


}