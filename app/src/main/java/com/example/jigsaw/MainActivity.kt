package com.example.jigsaw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstTile = jigsawView.engine.tileList.first()
        tile.tile.apply {
            this.capLeft = firstTile.capLeft
            this.capTop = firstTile.capTop
            this.capRight = firstTile.capRight
            this.capBottom = firstTile.capBottom
        }

    }
}
