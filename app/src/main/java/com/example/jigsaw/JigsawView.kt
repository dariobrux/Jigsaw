package com.example.jigsaw

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */

class JigsawView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    private var items = DEFAULT_ITEMS

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.JigsawView)
        items = typedArray.getInt(R.styleable.JigsawView_jv_pieces, items)
        typedArray.recycle()

        layoutManager = GridLayoutManager(context, items)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = (suggestedMinimumWidth + paddingLeft + paddingRight) + ((items / 2) * DEFAULT_TILE_SIZE)
        val desiredHeight = (suggestedMinimumHeight + paddingTop + paddingBottom) + ((items / 2) * DEFAULT_TILE_SIZE)

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
                result = Math.min(result, specSize)
            }
        }
        if (result < desiredSize) {
            Log.e("ChartView", "The view is too small, the content might get cut")
        }
        return result
    }


    companion object {
        const val DEFAULT_TILE_SIZE = 100
        const val DEFAULT_ITEMS = 4
    }
}