package com.example.jigsaw.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
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

    interface OnTileListener {
        fun onTileMove(view: View, motionEvent: MotionEvent): Boolean
    }

    fun init(tileList: ArrayList<Tile>, onTileListener: OnTileListener) {

        val viewTreeObserver: ViewTreeObserver = viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this)
                    addTiles(tileList, onTileListener)
//                    viewWidth = view.getWidth()
//                    viewHeight = view.getHeight()
                }
            })
        }
    }

    private fun addTiles(tileList: java.util.ArrayList<Tile>, onTileListener: OnTileListener) {
        tileList.shuffled().forEach { tile ->

            val tileView = TileView(context).apply {
                this.tile = tile
                this.setOnTouchListener { view, motionEvent -> onTileListener.onTileMove(view, motionEvent) }
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

//            tileView.outlineProvider = ViewOutlineProvider.BOUNDS
//            tileView.setBackgroundColor(Color.TRANSPARENT)
//            tileView.elevation = 20f

            tileView.animate().scaleX(0.75f).scaleY(0.75f).setDuration(0).startDelay
            tileView.bringToFront()
        }
    }
}