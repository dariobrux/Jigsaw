package com.dariobrux.jigsaw.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dariobrux.jigsaw.extensions.toInvisible
import com.dariobrux.jigsaw.extensions.toVisible
import com.dariobrux.jigsaw.interfaces.OnJigsawListenerAdapter
import com.dariobrux.jigsaw.interfaces.OnTileSelectedListener
import com.dariobrux.jigsaw.models.Tile
import com.dariobrux.jigsaw.models.TileEmpty
import com.dariobrux.jigsaw.models.TileFull
import com.dariobrux.jigsaw.widgets.TileView


class GridAdapter(private val context: Context, val itemList: MutableList<Tile>, val isSpread: Boolean, private val onTileSelectedListener: OnTileSelectedListener?) : RecyclerView.Adapter<ViewHolder>() {

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
        holder.tileView.toVisible()
        holder.tileView.tile = item
        holder.tileView.setOnClickListener {
            onTileSelectedListener?.onTileSelected(this, holder.tileView, item, position)
        }
        if (isSettled) {
            holder.tileView.toInvisible()
            holder.tileView.postDelayed({
                holder.tileView.toVisible()
                if (isSpread) {
                    onJigsawListenerAdapter?.onTileUnsettled(holder.tileView)
                } else {

                    if (isCompleted()) {
                        onJigsawListenerAdapter?.onTileSettled(holder.tileView, true)
                        onJigsawListenerAdapter?.onCompleted()
                    } else {
                        onJigsawListenerAdapter?.onTileSettled(holder.tileView, holder.tileView.tile.position == position)
                    }
                }
            }, 150)
            isSettled = false
        }
    }

    private fun isCompleted(): Boolean {
        var isCompleted = false
        var oldPosition = -1
        (itemList as ArrayList).forEach {
            if (it is TileEmpty) {
                isCompleted = false
                return@forEach
            }
            it as TileFull
            isCompleted = oldPosition + 1 == it.position
            oldPosition++
        }
        return isCompleted
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
            if (!isSettled) {
                tileView.setOnJigsawListener(onJigsawListenerAdapter)
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