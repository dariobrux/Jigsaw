package com.example.jigsaw.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import com.example.jigsaw.Constants.DEFAULT_SMALL_TILE_SCALE
import com.example.jigsaw.interfaces.OnJigsawListenerAdapter
import com.example.jigsaw.interfaces.OnTileSelectedListener
import com.example.jigsaw.models.Tile
import com.example.jigsaw.models.TileEmpty
import com.example.jigsaw.models.TileFull
import com.example.jigsaw.widgets.TileView


class GridAdapter(private val context: Context, val itemList: MutableList<Tile>, private val isSpread: Boolean, private val onTileSelectedListener: OnTileSelectedListener?) : RecyclerView.Adapter<ViewHolder>() {

    private var onJigsawListenerAdapter: OnJigsawListenerAdapter? = null
    private var isSettled = false

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            FULL -> {
                TileFullViewHolder(TileView(context))
            }
            else -> {
                TileEmptyViewHolder(TileView(context))
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
        holder.emptyView.setOnClickListener {
            onTileSelectedListener?.onEmptySelected(this, holder.emptyView, position)
        }
    }

    private fun configureTileFullViewHolder(holder: TileFullViewHolder, position: Int) {
        val item = itemList[position] as TileFull
        holder.tileView.tile = item
        holder.tileView.setOnClickListener {
            onTileSelectedListener?.onTileSelected(this, holder.tileView, item, position)
        }
        if (isSettled) {
            holder.tileView.postDelayed({
                if (isSpread) {
                    onJigsawListenerAdapter?.onTileDeselected(holder.tileView)
                } else {
                    onJigsawListenerAdapter?.onTileSettled(holder.tileView)
                }
            }, 200)
            isSettled = false
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

    fun setOnJigsawListener(listener: OnJigsawListenerAdapter) {
        this.onJigsawListenerAdapter = listener
    }

    fun prepareOnTileSettled() {
        isSettled = true
    }

    inner class TileFullViewHolder(view: TileView) : ViewHolder(view) {
        var tileView = view

        init {
//            if (smallTiles) {
//                view.animate().scaleY(DEFAULT_SMALL_TILE_SCALE).scaleX(DEFAULT_SMALL_TILE_SCALE).setDuration(0).start()
//            }

            if (!isSettled) {
                onJigsawListenerAdapter?.onTilePositioned(tileView)
            }
        }
    }

    inner class TileEmptyViewHolder(view: View) : ViewHolder(view) {
        var emptyView = view
    }

    companion object {
        private const val EMPTY = 0
        private const val FULL = 1
    }
}