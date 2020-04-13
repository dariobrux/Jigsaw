package com.dariobrux.jigsaw.interfaces

import android.view.View

/**
 * Created by Dario Bruzzese
 * on 4/9/2020
 */
interface OnJigsawListener {

    fun onCompleted()

    fun onTileDeselected(view: View, isInBoard: Boolean)

    fun onTileGenerated(view: View)

    fun onTileSelected(view: View)

    fun onTileSettled(view: View, isCorrectPosition: Boolean)

    fun onTileUnsettled(view: View)

}