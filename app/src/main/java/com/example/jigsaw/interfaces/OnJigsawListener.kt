package com.example.jigsaw.interfaces

import android.view.View
import android.view.ViewParent

/**
 * Created by Dario Bruzzese
 * on 4/9/2020
 */
interface OnJigsawListener {

    fun onTileSelected(view: View)

    fun onTileDeselected(view: View)

    fun onTileSettled(view: View)

    fun onTilePositioned(view: View)

    fun onCompleted()
}