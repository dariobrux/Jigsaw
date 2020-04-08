package com.example.jigsaw.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.jigsaw.models.Tile
import com.example.jigsaw.widgets.TileView
import com.example.jigsaw.adapters.GridAdapter.CustomViewHolder
import com.example.jigsaw.interfaces.OnTileSelectedListener

class GridAdapter(private val context: Context, private val itemList: List<Tile>, private val smallTiles: Boolean, private val onTileSelectedListener: OnTileSelectedListener?) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): CustomViewHolder {
        val view = TileView(context)
        view.setOnClickListener {
            onTileSelectedListener?.onTileSelected(view, itemList[position])
        }
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = itemList[position]
        holder.tileView.tile.capLeft = item.capLeft
        holder.tileView.tile.capTop = item.capTop
        holder.tileView.tile.capRight = item.capRight
        holder.tileView.tile.capBottom = item.capBottom
        holder.tileView.tile.index = item.index
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class CustomViewHolder(view: TileView) : ViewHolder(view) {
        var tileView = view

        init {
            if (smallTiles) {
                view.animate().scaleY(0.65f).scaleX(0.65f).setDuration(0).start()
            }
        }
    }
}