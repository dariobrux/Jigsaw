package com.example.jigsaw.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.jigsaw.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_jigsaw.view.*

/**
 * Created by Dario Bruzzese
 * on 4/8/2020
 */

class JigsawView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    init {
        inflate(getContext(), R.layout.layout_jigsaw, this)

//        val firstTile = gridView.engine.tileList.first()
//        tile.tile.apply {
//            this.capLeft = firstTile.capLeft
//            this.capTop = firstTile.capTop
//            this.capRight = firstTile.capRight
//            this.capBottom = firstTile.capBottom
//        }
    }

}