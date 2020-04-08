package com.example.jigsaw.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.jigsaw.Constants
import com.example.jigsaw.interfaces.OnTileSelectedListener
import com.example.jigsaw.models.Tile
import com.example.jigsaw.models.TileEmpty
import com.example.jigsaw.models.TileFull
import com.example.jigsaw.widgets.TileView


class GridAdapter(private val context: Context, val itemList: MutableList<Tile>, private val smallTiles: Boolean, private val onTileSelectedListener: OnTileSelectedListener?) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            FULL -> {
                TileFullViewHolder(TileView(context))
            }
            else -> {
                TileEmptyViewHolder(View(context))
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            FULL -> {
                val holder: TileFullViewHolder = viewHolder as TileFullViewHolder
                configureTileFullViewHolder(holder, position)
            }
            else -> {
                val holder: TileEmptyViewHolder = viewHolder as TileEmptyViewHolder
                configureTileEmptyViewHolder(holder, position)
            }
        }
    }

    private fun configureTileEmptyViewHolder(holder: TileEmptyViewHolder, position: Int) {
        holder.emptyView.setBackgroundColor(Color.BLACK)
        holder.emptyView.setOnClickListener {
            onTileSelectedListener?.onEmptySelected(holder.emptyView, position)
        }
    }

    private fun configureTileFullViewHolder(holder: TileFullViewHolder, position: Int) {
        val item = itemList[position] as TileFull
        holder.tileView.tile = item
        holder.tileView.setOnClickListener {
            onTileSelectedListener?.onTileSelected(holder.tileView, item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (itemList[position] is TileEmpty) {
            return EMPTY
        } else if (itemList[position] is TileFull) {
            return FULL
        }
        return -1
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class TileFullViewHolder(view: TileView) : ViewHolder(view) {
        var tileView = view

        init {
            if (smallTiles) {
                view.animate().scaleY(0.65f).scaleX(0.65f).setDuration(0).start()
            }
        }
    }

    inner class TileEmptyViewHolder(view: View) : ViewHolder(view) {
        var emptyView = view

        init {
            emptyView.layoutParams = FrameLayout.LayoutParams(Constants.DEFAULT_TILE_SIZE, Constants.DEFAULT_TILE_SIZE)
        }
    }

    companion object {
        private const val EMPTY = 0
        private const val FULL = 1
    }
}