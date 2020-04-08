package com.example.jigsaw.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.jigsaw.Constants
import com.example.jigsaw.Engine
import com.example.jigsaw.R
import kotlinx.android.synthetic.main.layout_jigsaw.view.*
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Created by Dario Bruzzese
 * on 4/8/2020
 */

class JigsawView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    private var rows = 0
    private var cols = 0
    private var items = 0

    init {
        inflate(getContext(), R.layout.layout_jigsaw, this)

        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.JigsawView)
        items = typedArray.getInt(R.styleable.JigsawView_jv_pieces, Constants.DEFAULT_ITEMS)
        typedArray.recycle()

        if (isPerfectSquare(items)) {
            rows = getRows()
            cols = getCols()
        } else {
            val x = getDivisor(items)
            val y = items / x
            rows = min(x, y)
            cols = max(x, y)
        }

        val engine = Engine(items, rows, cols)

        gridView.init(engine.tileList, rows, cols)
        spreadView.init(engine.tileList)

//        val firstTile = gridView.engine.tileList.first()
//        tile.tile.apply {
//            this.capLeft = firstTile.capLeft
//            this.capTop = firstTile.capTop
//            this.capRight = firstTile.capRight
//            this.capBottom = firstTile.capBottom
//        }
    }


    private fun isPerfectSquare(x: Int): Boolean {
        val sq = sqrt(x.toDouble())
        return sq - floor(sq) == 0.0
    }

    private fun getDivisor(x: Int): Int {
        return when {
            x % 4 == 0 -> {
                4
            }
            x % 3 == 0 -> {
                3
            }
            x % 2 == 0 -> {
                2
            }
            else -> {
                x
            }
        }
    }

    private fun getRows(): Int = sqrt(items.toDouble()).toInt()
    private fun getCols(): Int = sqrt(items.toDouble()).toInt()
}