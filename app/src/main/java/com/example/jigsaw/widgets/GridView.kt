package com.example.jigsaw.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jigsaw.Constants.DEFAULT_TILE_SIZE
import com.example.jigsaw.adapters.GridAdapter
import com.example.jigsaw.models.Tile

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */

class GridView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    var rows = 0
    var cols = 0

    fun init(tileList: List<Tile>, rows: Int, cols: Int, smallTiles: Boolean) {
        this.rows = rows
        this.cols = cols
        layoutManager = GridLayoutManager(context, cols)
        adapter = GridAdapter(context, tileList, smallTiles)
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
}