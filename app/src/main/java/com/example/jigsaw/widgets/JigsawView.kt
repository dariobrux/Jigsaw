package com.example.jigsaw.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.example.jigsaw.Constants
import com.example.jigsaw.Engine
import com.example.jigsaw.R
import com.example.jigsaw.adapters.GridAdapter
import com.example.jigsaw.interfaces.OnTileSelectedListener
import com.example.jigsaw.models.Tile
import com.example.jigsaw.models.TileEmpty
import com.example.jigsaw.models.TileFull
import kotlinx.android.synthetic.main.layout_jigsaw.view.*
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Created by Dario Bruzzese
 * on 4/8/2020
 */

class JigsawView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet), OnTileSelectedListener {

    private var rows = 0
    private var cols = 0
    private var items = 0

    private val engine: Engine

    private var selectedAdapter : GridAdapter? = null
    private var selectedTile: Tile? = null
    private var selectedPosition: Int = -1

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

        engine = Engine(context, items, rows, cols)

        gridView.init(engine.tileEmptyList, rows, cols, false, this)
        spreadView.init(engine.tileFullList.shuffled().toMutableList(), rows, cols, true, this)
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

    override fun onTileSelected(adapter: GridAdapter, view: TileView, tile: TileFull, position: Int) {
        selectedAdapter = adapter
        selectedTile = tile
        selectedPosition = position
    }

    override fun onEmptySelected(adapter: GridAdapter, view: View, position: Int) {
        if (selectedTile == null || selectedPosition == -1 || selectedAdapter == null) {
            return
        }

        selectedAdapter!!.itemList[selectedPosition] = TileEmpty()
        selectedAdapter!!.notifyItemChanged(selectedPosition)

        engine.tileEmptyList[position] = selectedTile!!
        gridView.adapter!!.notifyItemChanged(position)

        selectedTile = null
        selectedPosition = -1
    }
}