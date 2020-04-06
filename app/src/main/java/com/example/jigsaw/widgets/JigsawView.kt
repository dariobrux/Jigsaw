package com.example.jigsaw.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jigsaw.Engine
import com.example.jigsaw.R
import com.example.jigsaw.adapters.JigsawAdapter
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */

class JigsawView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    private var items = DEFAULT_ITEMS
    private var rows = 0
    private var cols = 0

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.JigsawView)
        items = typedArray.getInt(R.styleable.JigsawView_jv_pieces, items)
        typedArray.recycle()

        if (isPerfectSquare(items)) {
            rows = getRows()
            cols = getCols()
        } else {
            val x = getDivisor(items)
            val y = items / x // 2
            rows = min(x, y)
            cols = max(x, y)
        }

        val engine = Engine(items, rows, cols)

        layoutManager = GridLayoutManager(context, cols)
        adapter = JigsawAdapter(context, engine.tileList)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = (suggestedMinimumWidth + paddingLeft + paddingRight) + ((cols) * DEFAULT_TILE_SIZE)
        val desiredHeight = (suggestedMinimumHeight + paddingTop + paddingBottom) + ((rows) * DEFAULT_TILE_SIZE)

        setMeasuredDimension(
            measureDimension(desiredWidth, widthMeasureSpec),
            measureDimension(desiredHeight, heightMeasureSpec)
        )
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = result.coerceAtMost(specSize)
            }
        }
        return result
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

    companion object {
        const val DEFAULT_TILE_SIZE = 100
        const val DEFAULT_ITEMS = 4
    }
}