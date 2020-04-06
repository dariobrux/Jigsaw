package com.example.jigsaw

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.jigsaw.JigsawAdapter.CustomViewHolder

class JigsawAdapter(private val context: Context, private val itemList: List<Tile>?) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomViewHolder {
        val view = TileView(context)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, i: Int) {
        val item = itemList!![i]
        holder.tileView.tile.capLeft = item.capLeft
        holder.tileView.tile.capTop = item.capTop
        holder.tileView.tile.capRight = item.capRight
        holder.tileView.tile.capBottom = item.capBottom
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    inner class CustomViewHolder(view: TileView) : ViewHolder(view) {
        var tileView = view
    }
}