package com.example.jigsaw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import com.example.jigsaw.interfaces.OnJigsawListenerAdapter
import com.example.jigsaw.widgets.TileView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jigsawView?.setOnJigsawListener(object : OnJigsawListenerAdapter() {

            override fun onTileSelected(view: View) {
                view.animate().scaleX(0.8f).scaleY(0.8f).setInterpolator(OvershootInterpolator()).setDuration(400).start()
            }

            override fun onTileDeselected(view: View) {
                view.animate().scaleX(0.65f).scaleY(0.65f).setInterpolator(OvershootInterpolator()).setDuration(400).start()
            }

            override fun onTileSettled(view: View) {
                view.scaleX = 0f
                view.scaleY = 0f
                view.animate().scaleX(1f).scaleY(1f).setInterpolator(OvershootInterpolator()).setDuration(400).setStartDelay(200).start()

            }
        })
    }
}
