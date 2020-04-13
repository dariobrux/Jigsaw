package com.example.jigsaw.widgets

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.example.jigsaw.Constants
import com.example.jigsaw.Engine
import com.example.jigsaw.R
import com.example.jigsaw.adapters.GridAdapter
import com.example.jigsaw.interfaces.OnJigsawListenerAdapter
import com.example.jigsaw.interfaces.OnTileSelectedListener
import com.example.jigsaw.models.Tile
import com.example.jigsaw.models.TileDecorator
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

    var pieces = 0
        set(value) {
            field = value
            if (isPerfectSquare(value)) {
                rows = getRows()
                cols = getCols()
            } else {
                val x = getDivisor(value)
                val y = value / x
                rows = min(x, y)
                cols = max(x, y)
            }
        }

    var bitmap: Bitmap? = null
        set(value) {
            field = value
            if (value == null) return
            engine = Engine(value, pieces, rows, cols, tileDecorator)
            gridView.init(engine!!.tileEmptyList, rows, cols, false, this)
            spreadView.init(engine!!.tileFullList.shuffled().toMutableList(), rows, spreadCols, true, this)
        }

    var spreadCols = 1

    var tileDecorator = TileDecorator()

    private var rows = 0
    private var cols = 0

    private val listenerHolder = ListenerHolder()
    private var onJigsawListenerAdapter: OnJigsawListenerAdapter? = null

    private var engine: Engine? = null

    private var selectedAdapter: GridAdapter? = null
    private var selectedTile: Tile? = null
    private var selectedPosition: Int = -1
    private var selectedView: TileView? = null

    init {
        inflate(getContext(), R.layout.layout_jigsaw, this)

        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.JigsawView)
        tileDecorator.borderColor = typedArray.getColor(R.styleable.JigsawView_jv_tileBorderColor, tileDecorator.borderColor)
        tileDecorator.borderWidth = typedArray.getDimension(R.styleable.JigsawView_jv_tileBorderWidth, tileDecorator.borderWidth)
        pieces = typedArray.getInt(R.styleable.JigsawView_jv_pieces, Constants.DEFAULT_ITEMS)
        typedArray.getDrawable(R.styleable.JigsawView_jv_borderBoard)?.let {
            gridView?.background = it
        }

        spreadCols = typedArray.getInt(R.styleable.JigsawView_jv_spreadBoardCols, cols)
        typedArray.recycle()
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

    private fun getRows(): Int = sqrt(pieces.toDouble()).toInt()
    private fun getCols(): Int = sqrt(pieces.toDouble()).toInt()

    fun setOnJigsawListener(listener: OnJigsawListenerAdapter) {
        this.onJigsawListenerAdapter = listener
        gridView?.setOnJigsawListener(listener)
        spreadView?.setOnJigsawListener(listener)
    }

    override fun onTileSelected(adapter: GridAdapter, view: TileView, tile: TileFull, position: Int) {

        val isInBoard = !adapter.isSpread

        // If is the same selected tile.
        if (selectedTile == tile) {
            onJigsawListenerAdapter?.onTileDeselected(listenerHolder.selectedTileView!!, isInBoard)
            recycle()
            return
        }

        // If another tile has been selected, I must replace the selected view with the new view.
        if (selectedTile is TileFull) {

            // If the grids are the same.
            if (adapter == selectedAdapter) {
                replaceTileAndRecycle(adapter, adapter, view, tile, position)
            } else {
                // If the grids are different
                replaceTileAndRecycle(adapter, selectedAdapter!!, view, tile, position)
            }
            return
        }

        selectedAdapter = adapter
        selectedTile = tile
        selectedPosition = position
        selectedView = view

        // If the user invokes the OnJigsawListenerAdapter, when he selects a tile, the selected and
        // deselected callbacks are invoked.
        if (listenerHolder.selectedTileView != null && listenerHolder.selectedTileView != view) {
            onJigsawListenerAdapter?.onTileDeselected(listenerHolder.selectedTileView!!, isInBoard)
        }
        onJigsawListenerAdapter?.onTileSelected(view)
        listenerHolder.selectedTileView = view
    }

    private fun replaceTileAndRecycle(adapterFrom: GridAdapter, adapterTo: GridAdapter, view: TileView, tile: TileFull, position: Int) {
        adapterFrom.apply {
            view.reset()
            itemList[position] = selectedTile!!
            prepareOnTileSettled()
            notifyItemChanged(position)
            post {
                adapterTo.apply {
                    selectedView?.reset()
                    itemList[selectedPosition] = tile
                    prepareOnTileSettled()
                    notifyItemChanged(selectedPosition)

                    recycle()
                }
            }
        }
    }

    override fun onEmptySelected(adapter: GridAdapter, view: View, position: Int) {
        if (selectedTile == null || selectedPosition == -1 || selectedAdapter == null || selectedView == null) {
            return
        }

        selectedView?.reset()
        selectedAdapter!!.itemList[selectedPosition] = TileEmpty()
        selectedAdapter!!.notifyItemChanged(selectedPosition)

        adapter.itemList[position] = selectedTile!!
        adapter.prepareOnTileSettled()
        adapter.notifyItemChanged(position)

        recycle()
    }

    private fun recycle() {
        selectedPosition = -1
        selectedAdapter = null
        selectedTile = null
        selectedView = null
        listenerHolder.selectedTileView = null
    }

    inner class ListenerHolder {
        var selectedTileView: TileView? = null
    }
}