package com.example.jigsaw

import android.util.Log
import com.example.jigsaw.enums.CapMode
import com.example.jigsaw.enums.TilePosition
import com.example.jigsaw.models.Tile

/**
 * Created by Dario Bruzzese
 * on 4/5/2020
 */
class Engine(items: Int, private val rows: Int, private val cols: Int) {

    var tileList = arrayListOf<Tile>()

    init {

        Log.d("Engine", "$items items is -> ${rows}x$cols")

        var currentRightCap: CapMode = getRandomCapMode()
        val currentBottomCapList = mutableListOf<CapMode>()

        repeat(rows) { i ->
            repeat(cols) { j ->
                val position = getPosition(i, j)
                Log.d("Engine", "$i, $j -> $position ")
                val tile = Tile().apply {
                    when (position) {
                        TilePosition.LEFT_TOP -> {
                            this.capLeft = CapMode.NONE
                            this.capTop = CapMode.NONE
                            this.capRight = currentRightCap
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.TOP -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = CapMode.NONE
                            this.capRight = getRandomCapMode()
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.RIGHT_TOP -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = CapMode.NONE
                            this.capRight = CapMode.NONE
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.LEFT -> {
                            this.capLeft = CapMode.NONE
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.CENTER -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.RIGHT -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = CapMode.NONE
                            this.capBottom = getRandomCapMode()
                        }
                        TilePosition.LEFT_BOTTOM -> {
                            this.capLeft = CapMode.NONE
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = CapMode.NONE
                        }
                        TilePosition.BOTTOM -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = getRandomCapMode()
                            this.capBottom = CapMode.NONE
                        }
                        TilePosition.RIGHT_BOTTOM -> {
                            this.capLeft = currentRightCap.inverse()
                            this.capTop = currentBottomCapList[j].inverse()
                            this.capRight = CapMode.NONE
                            this.capBottom = CapMode.NONE
                        }
                    }
                }
                currentRightCap = tile.capRight
                currentBottomCapList.add(tile.capBottom)
                tileList.add(tile)
                Log.d("Engine", "$i, $j -> $currentBottomCapList ")
            }
            if (i != 0) {
                currentBottomCapList.subList(0, currentBottomCapList.size / 2).clear()
            }
        }
    }

    private fun getRandomCapMode(): CapMode {
        return listOf(CapMode.FULL, CapMode.EMPTY).shuffled().first()
    }


    private fun getPosition(row: Int, col: Int): TilePosition {
        return if (!(row == 0 || col == 0 || row == rows - 1 || col == cols - 1)) {
            TilePosition.CENTER
        } else {
            val isLeft = (col == 0)
            val isRight = (col == cols - 1)
            val isTop = (row == 0)
            val isBottom = (row == rows - 1)
            return when {
                isLeft && isTop -> TilePosition.LEFT_TOP
                isLeft && isBottom -> TilePosition.LEFT_BOTTOM
                isLeft -> TilePosition.LEFT
                isRight && isTop -> TilePosition.RIGHT_TOP
                isRight && isBottom -> TilePosition.RIGHT_BOTTOM
                isRight -> TilePosition.RIGHT
                isTop -> TilePosition.TOP
                isBottom -> TilePosition.BOTTOM
                else -> TilePosition.CENTER
            }
        }
    }
}