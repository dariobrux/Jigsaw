package com.example.jigsaw.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import com.example.jigsaw.Constants
import com.example.jigsaw.models.Tile


/**
 * Created by Dario Bruzzese
 * on 4/8/2020
 */

class SpreadView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    fun init(tileList: ArrayList<Tile>) {

        val viewTreeObserver: ViewTreeObserver = viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this)
                    addTiles(tileList)
//                    viewWidth = view.getWidth()
//                    viewHeight = view.getHeight()
                }
            })
        }
    }

    private fun addTiles(tileList: java.util.ArrayList<Tile>) {
        tileList.shuffled().forEach { tile ->
            val tileView = TileView(context).apply {
                this.tile = tile
            }
            addView(tileView)

            val leftRange = IntRange(
                Constants.DEFAULT_CAP_RADIUS.toInt(),
                (width - Constants.DEFAULT_TILE_SIZE - (2 * Constants.DEFAULT_CAP_RADIUS)).toInt()
            )

            val topRange = IntRange(
                Constants.DEFAULT_CAP_RADIUS.toInt(),
                (height - Constants.DEFAULT_TILE_SIZE - (2 * Constants.DEFAULT_CAP_RADIUS)).toInt()
            )

            (tileView.layoutParams as MarginLayoutParams).leftMargin = leftRange.shuffled().first()
            (tileView.layoutParams as MarginLayoutParams).topMargin = topRange.shuffled().first()
        }
    }
}